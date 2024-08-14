package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dto.MicroQuizDto;
import kg.attractor.online_quiz_platform.dto.MiniQuizDto;
import kg.attractor.online_quiz_platform.dto.QuizAnswerDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.QuizRateDto;
import kg.attractor.online_quiz_platform.dto.QuizResultDto;

import java.util.List;

public interface QuizService {
    List<MicroQuizDto> getQuizzes();

    void saveQuiz(QuizDto quiz);

    QuizDto getQuizById(long id);

    MiniQuizDto getMiniQuizById(long quizId);

    void saveQuizAnswer(QuizAnswerDto quizAnswerDto);

    List<QuizResultDto> getQuizResults(long quizId);

    void rateQuiz(QuizRateDto quizRateDto);

    List<QuizRateDto> getQuizzesRates();

    List<QuizResultDto> getSortedQuizResults(long quizId);

}
