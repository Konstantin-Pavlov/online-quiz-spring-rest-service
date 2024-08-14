package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dto.QuestionDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getQuestions();

    List<QuestionDto> getQuestionsByQuizId(long id);

    void createQuestion(QuestionDto questionDto);
}
