package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.QuestionFull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionFullDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<QuestionFull> getFullQuestionsByQuizId(long id) {
        String sql = """
                SELECT * FROM QUESTIONS
                WHERE QUIZ_ID=?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(QuestionFull.class), id);
    }
}

