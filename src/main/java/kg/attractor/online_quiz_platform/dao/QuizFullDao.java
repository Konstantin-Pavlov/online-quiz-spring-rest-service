package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.dto.OptionDto;
import kg.attractor.online_quiz_platform.dto.QuestionFullDto;
import kg.attractor.online_quiz_platform.dto.QuizFullDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizFullDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void createFullQuiz(QuizFullDto quiz) {
        String sql = """
                    INSERT INTO QUIZZES (TITLE, DESCRIPTION, CREATOR_ID)
                    VALUES (:title, :description, :creatorId);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", quiz.getId())
                .addValue("title", quiz.getTitle())
                .addValue("description", quiz.getDescription())
                .addValue("creatorId", quiz.getCreatorId());
        namedParameterJdbcTemplate.update(sql, params);

        for (QuestionFullDto question : quiz.getQuestions()) {
            createQuestion(question);
        }
    }

    private void createQuestion(QuestionFullDto question) {
        String sql = """
                    INSERT INTO QUESTIONS (QUIZ_ID, QUESTION_TEXT)
                    VALUES (:quizId, :questionText);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", question.getId())
                .addValue("quizId", question.getQuizId())
                .addValue("questionText", question.getQuestionText());
        namedParameterJdbcTemplate.update(sql, params);

        for (OptionDto option : question.getOptions()) {
            createOption(option);
        }
    }

    private void createOption(OptionDto option) {
        String sql = """
                    INSERT INTO OPTIONS ("QUESTION_ID", "OPTION_TEXT", "IS_CORRECT")
                    VALUES (:questionId, :optionText, :correct);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", option.getId())
                .addValue("questionId", option.getQuestionId())
                .addValue("optionText", option.getOptionText())
                .addValue("correct", option.isCorrect());
        namedParameterJdbcTemplate.update(sql, params);
    }
}

