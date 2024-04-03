package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.QuizFullDto;

import java.util.List;

public interface QuizService {
    List<QuizDto> getQuizzes();
    List<QuizFullDto> getFullQuizzes();
    void createQuiz(QuizDto quiz);

    void createQuizFull(QuizFullDto quiz);
}
