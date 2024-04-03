package kg.attractor.online_quiz_platform.service.impl;

import kg.attractor.online_quiz_platform.dao.QuizDao;
import kg.attractor.online_quiz_platform.dao.QuizFullDao;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.QuizFullDto;
import kg.attractor.online_quiz_platform.model.Quiz;
import kg.attractor.online_quiz_platform.model.QuizFull;
import kg.attractor.online_quiz_platform.service.QuestionService;
import kg.attractor.online_quiz_platform.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizDao quizDao;
    private final QuizFullDao quizFullDao;
    private final QuestionService questionService;

    @Override
    public List<QuizDto> getQuizzes() {
        List<Quiz> quizzes = quizDao.getQuizzes();
        List<QuizDto> dtos = new ArrayList<>();
        quizzes.forEach(quiz ->
                dtos.add(QuizDto.builder()
                        .id(quiz.getId())
                        .title(quiz.getTitle())
                        .description(quiz.getDescription())
                        .creatorId(quiz.getCreatorId())
                        .build()
                )
        );
        return dtos;
    }

    @Override
    public List<QuizFullDto> getFullQuizzes() {
        List<QuizFull> quizzes = quizDao.getFullQuizzes();
        List<QuizFullDto> dtos = new ArrayList<>();
        quizzes.forEach(quiz ->
                dtos.add(QuizFullDto.builder()
                        .id(quiz.getId())
                        .title(quiz.getTitle())
                        .description(quiz.getDescription())
                        .questions(questionService.getFUllQuestionsByQuizId(quiz.getId()))
                        .creatorId(quiz.getCreatorId())
                        .build()
                )
        );
        return dtos;
    }

    @Override
    public void createQuiz(QuizDto quiz) {
        quizDao.createQuiz(quiz);
        log.info("added quiz with title " + quiz.getTitle());
    }

    @Override
    public void createQuizFull(QuizFullDto quiz) {
        quizFullDao.createFullQuiz(quiz);
        log.info("added quiz with title " + quiz.getTitle());
    }
}
