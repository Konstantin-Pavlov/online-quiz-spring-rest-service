package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.dto.QuestionDto;
import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.model.Question;
import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Question> getQuestions() {
        String sql = """
                    select * from QUESTIONS;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Question.class));
    }

    public void createQuestion(QuestionDto question) {
        String sql = """
                    insert into QUESTIONS(QUIZ_ID, QUESTION_TEXT)
                    values(:quiz_id, :question_text);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("quiz_id", question.getQuizId())
                .addValue("question_text", question.getQuestionText());
        namedParameterJdbcTemplate.update(sql, params);
    }
}
