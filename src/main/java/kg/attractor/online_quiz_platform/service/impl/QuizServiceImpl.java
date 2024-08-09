package kg.attractor.online_quiz_platform.service.impl;

import kg.attractor.online_quiz_platform.dao.QuizDao;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.exception.QuizNotFoundException;
import kg.attractor.online_quiz_platform.mapper.QuestionMapper;
import kg.attractor.online_quiz_platform.mapper.QuizMapper;
import kg.attractor.online_quiz_platform.model.Question;
import kg.attractor.online_quiz_platform.model.Quiz;
import kg.attractor.online_quiz_platform.service.QuestionService;
import kg.attractor.online_quiz_platform.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;
    private final QuestionService questionService;


    @Override
    public List<QuizDto> getQuizzes() {
        List<Quiz> quizzes = quizDao.getQuizzes();
        List<QuizDto> quizDtos = new ArrayList<>();
        quizzes.forEach(quiz ->
                quizDtos.add(QuizDto.builder()
                        .id(quiz.getId())
                        .title(quiz.getTitle())
                        .description(quiz.getDescription())
                        .questions(questionService.getQuestionsByQuizId(quiz.getId()))
                        .creatorId(quiz.getCreatorId())
                        .build()
                )
        );
        return quizDtos;
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
            List<Question> questionsByQuizId = questionService.getQuestionsByQuizId(quiz.get().getId())
                    .stream()
                    .map(QuestionMapper::fromDto)
                    .toList();

            quiz.get().setQuestions(questionsByQuizId);
            System.out.println();
            return QuizMapper.toDto(quiz.get());
        } else {
            throw new QuizNotFoundException(String.format("Quiz with id %s not found", id));
        }

    }
}
