package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.model.Quiz;
import kg.attractor.online_quiz_platform.model.QuizFull;
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
public class QuizDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Quiz> getQuizzes() {
        String sql = """
                    select * from QUIZZES;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Quiz.class));
    }

    public void createQuiz(QuizDto quiz) {
        String sql = """
                    insert into QUIZZES(TITLE, DESCRIPTION, CREATOR_ID)
                    values(:title, :description, :creator_id);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", quiz.getTitle())
                .addValue("description", quiz.getDescription())
                .addValue("creator_id", quiz.getCreatorId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public List<QuizFull> getFullQuizzes() {
        String sql = """
                    select * from QUIZZES;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(QuizFull.class));
    }
}
