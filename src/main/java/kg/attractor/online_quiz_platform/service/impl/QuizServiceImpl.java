package kg.attractor.online_quiz_platform.service.impl;

import kg.attractor.online_quiz_platform.dao.QuizDao;
import kg.attractor.online_quiz_platform.dao.UserDao;
import kg.attractor.online_quiz_platform.dto.OptionDto;
import kg.attractor.online_quiz_platform.dto.QuestionDto;
import kg.attractor.online_quiz_platform.dto.QuizAnswerDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.QuizOnlyWithQuestionsNumberDto;
import kg.attractor.online_quiz_platform.dto.QuizRateDto;
import kg.attractor.online_quiz_platform.dto.QuizResultDto;
import kg.attractor.online_quiz_platform.exception.InvalidQuizAnswerException;
import kg.attractor.online_quiz_platform.exception.QuizNotFoundException;
import kg.attractor.online_quiz_platform.exception.UserAlreadyRatedQuizException;
import kg.attractor.online_quiz_platform.exception.UserNotFoundException;
import kg.attractor.online_quiz_platform.mapper.QuestionMapper;
import kg.attractor.online_quiz_platform.mapper.QuizMapper;
import kg.attractor.online_quiz_platform.mapper.QuizRateMapper;
import kg.attractor.online_quiz_platform.model.Question;
import kg.attractor.online_quiz_platform.model.Quiz;
import kg.attractor.online_quiz_platform.model.QuizAnswer;
import kg.attractor.online_quiz_platform.model.QuizRate;
import kg.attractor.online_quiz_platform.service.QuestionService;
import kg.attractor.online_quiz_platform.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;
    private final QuestionService questionService;
    private final UserDao userDao;


    @Override
    public List<QuizOnlyWithQuestionsNumberDto> getQuizzes() {
        List<Quiz> quizzes = quizDao.getQuizzes();
        List<QuizOnlyWithQuestionsNumberDto> dtos = new ArrayList<>();
        quizzes.forEach(quiz -> dtos.add(
                QuizOnlyWithQuestionsNumberDto.builder()
                        .id(quiz.getId())
                        .title(quiz.getTitle())
                        .countQuestions(questionService.getQuestionsByQuizId(quiz.getId()).size())
                        .build())
        );
        return dtos;
    }

    @Override
    public void saveQuiz(QuizDto quiz) {
        quizDao.saveQuiz(quiz);
    }

    @Override
    public QuizDto getQuizById(long id) {
        Optional<Quiz> quiz = quizDao.getQuizById(id);
        if (quiz.isPresent()) {
//            List<Question> questionsByQuizId = questionDao.getQuestionsByQuizId(quiz.get().getId());
            List<Question> questionsByQuizId = questionService.getQuestionsByQuizId(quiz.get().getId()).stream().map(QuestionMapper::fromDto).toList();

            quiz.get().setQuestions(questionsByQuizId);
            System.out.println();
            return QuizMapper.toDto(quiz.get());
        } else {
            throw new QuizNotFoundException(String.format("Quiz with id %s not found", id));
        }

    }

    @Override
    public void saveQuizAnswer(QuizAnswerDto quizAnswerDto) {
        // check if quiz and user exists
        checkIfQuizAndUserExist(quizAnswerDto.getQuizId(), quizAnswerDto.getUserId());

        // Validate the number of answers
        int numberOfQuestions = quizDao.countQuestionsByQuizId(quizAnswerDto.getQuizId());
        if (quizAnswerDto.getAnswers().size() != numberOfQuestions) {
            log.error(
                    "The number of answers ({}) does not match the number of questionsNumber ({}) in the quiz (id {})",
                    numberOfQuestions, numberOfQuestions, quizAnswerDto.getQuizId()
            );
            throw new InvalidQuizAnswerException("The number of answers does not match the number of questionsNumber in the quiz");
        }

        // Save each answer
        quizAnswerDto.getAnswers().forEach((questionId, optionId) -> {
            QuizAnswer quizAnswer = new QuizAnswer();
            quizAnswer.setUserId(quizAnswerDto.getUserId());
            quizAnswer.setQuizId(quizAnswerDto.getQuizId());
            quizAnswer.setQuestionId(questionId);
            quizAnswer.setOptionId(optionId);
            quizAnswer.setTimestamp(LocalDateTime.now());
            quizDao.saveQuizAnswer(quizAnswer);
        });

        quizDao.saveUserQuizSubmission(quizAnswerDto.getUserId(), quizAnswerDto.getQuizId());
        log.info("Saved answer for quiz with id: {}", quizAnswerDto.getQuizId());

        calculateAndSaveUserScore(quizAnswerDto);

    }

    @Override
    public List<QuizResultDto> getQuizResults(long quizId) {
        return quizDao.getQuizResults(quizId).stream().map(quizResultDto -> {
            QuizDto quizDto = getQuizById(quizId);
            int totalQuestions = quizDto.getQuestions().size();
            int correctAnswers = quizResultDto.getScore();
            int incorrectAnswers = totalQuestions - quizResultDto.getScore();
            quizResultDto.setScore(correctAnswers);
            quizResultDto.setTotalQuestionsNumber(totalQuestions);
            quizResultDto.setCorrectAnswers(correctAnswers);
            quizResultDto.setWrongAnswers(incorrectAnswers);
            return quizResultDto;
        }).toList();
    }

    @Override
    public void rateQuiz(QuizRateDto quizRateDto) {
        checkIfQuizAndUserExist(quizRateDto.getQuizId(), quizRateDto.getUserId());
        if (quizDao.hasUserRatedQuiz(quizRateDto.getQuizId(), quizRateDto.getUserId())) {
            throw new UserAlreadyRatedQuizException(
                    String.format("User with id %d has already rated quiz with id %d",
                            quizRateDto.getUserId(), quizRateDto.getQuizId())
            );
        }
        quizDao.saveQuizRate(quizRateDto);
        log.info("quiz with id {} rated to {}", quizRateDto.getQuizId(), quizRateDto.getRate());
    }

    @Override
    public List<QuizRateDto> getQuizzesRates() {
        List<QuizRate> quizRates = quizDao.getQuizzesRates();
        return quizRates.stream().map(QuizRateMapper::toQuizRateDto).toList();
    }

    @Override
    public List<QuizResultDto> getSortedQuizResults(long quizId) {
        return getQuizResults(quizId).stream()
                .sorted(Comparator.comparing(QuizResultDto::getScore).reversed())
                .toList();
    }

    private void calculateAndSaveUserScore(QuizAnswerDto quizAnswerDto) {
        QuizDto quizDto = getQuizById(quizAnswerDto.getQuizId());

        int score = 0;

        for (QuestionDto question : quizDto.getQuestions()) {
            Long userAnswer = quizAnswerDto.getAnswers().get(question.getId());
            if (userAnswer != null) {
                for (OptionDto option : question.getOptions()) {
                    if (option.getId().equals(userAnswer) && option.isCorrect()) {
                        score++;
                        break;
                    }
                }
            }
        }

        // Save the score in the quiz_results table
        quizDao.saveQuizResult(quizAnswerDto.getUserId(), quizAnswerDto.getQuizId(), score);

        log.info("score calculated: {}. Results saved", score);
    }

    private void checkIfQuizAndUserExist(long quizId, long userId) {
        // check if quiz exists
        if (quizExists(quizId)) {
            log.error(String.format("Quiz with id %s not found", quizId));
            throw new QuizNotFoundException(String.format("Quiz with id %s not found", quizId));
        }

        // check if user exists
        if (userExists(userId)) {
            log.error(String.format("User with id %s not found", userId));
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }
    }

    private boolean quizExists(long quizId) {
        return quizDao.getQuizById(quizId).isEmpty();
    }

    private boolean userExists(long userId) {
        return userDao.getUserById(userId).isEmpty();
    }

}