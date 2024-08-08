package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dto.QuizDto;

import java.util.List;

public interface QuizService {
    List<QuizDto> getQuizzes();

    void saveQuiz(QuizDto quiz);
}
