package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dto.QuestionDto;
import kg.attractor.online_quiz_platform.dto.QuestionFullDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getQuestions();
    List<QuestionFullDto> getFUllQuestionsByQuizId(long id);
    void createQuestion(QuestionDto question);
}
