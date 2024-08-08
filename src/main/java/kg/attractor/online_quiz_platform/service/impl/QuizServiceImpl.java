package kg.attractor.online_quiz_platform.service.impl;

import kg.attractor.online_quiz_platform.dao.QuizDao;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.model.Quiz;
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
    private final QuestionService questionService;


    @Override
    public List<QuizDto> getQuizzes() {
        List<Quiz> quizzes = quizDao.getFullQuizzes();
        List<QuizDto> quizDtos = new ArrayList<>();
        quizzes.forEach(quiz ->
                quizDtos.add(QuizDto.builder()
                        .id(quiz.getId())
                        .title(quiz.getTitle())
                        .description(quiz.getDescription())
                        .questions(questionService.getFUllQuestionsByQuizId(quiz.getId()))
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
}
