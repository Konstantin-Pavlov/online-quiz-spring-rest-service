package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dto.QuizAnswerDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.ResultDto;

import java.util.List;

public interface QuizService {
    List<QuizDto> getQuizzes();

    void saveQuiz(QuizDto quiz);

    QuizDto getQuizById(long id);

    void saveQuizAnswer(QuizAnswerDto quizAnswerDto);


    ResultDto getQuizResults(long userId, long id);
}
