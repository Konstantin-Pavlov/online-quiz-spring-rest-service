package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dto.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestions();
    List<Question> getFUllQuestionsByQuizId(long id);
    void createQuestion(Question question);
}
