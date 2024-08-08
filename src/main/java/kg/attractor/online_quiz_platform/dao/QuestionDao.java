package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.dto.OptionDto;
import kg.attractor.online_quiz_platform.dto.Question;
import kg.attractor.online_quiz_platform.model.QuestionWithOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private OptionDao optionDao;


    public List<QuestionWithOptions> getFullQuestionsByQuizId(long id) {
        String sql = """
                SELECT * FROM QUESTIONS
                WHERE QUIZ_ID=?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(QuestionWithOptions.class), id);
    }


    public List<kg.attractor.online_quiz_platform.model.Question> getQuestions() {
        String sql = """
                    select * from QUESTIONS;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(kg.attractor.online_quiz_platform.model.Question.class));
    }

    public void createQuestion(Question question) {
        String sql = """
                    insert into QUESTIONS(QUIZ_ID, QUESTION_TEXT)
                    values(:quiz_id, :question_text);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("quiz_id", question.getQuizId())
                .addValue("question_text", question.getQuestionText());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        // Check if the keyHolder contains the generated key
        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("Failed to retrieve the generated key for the new question.");
        }

        long questionGeneratedId = keyHolder.getKey().longValue();

        for (OptionDto option : question.getOptions()) {
            option.setQuestionId(questionGeneratedId);
            optionDao.saveOption(option);
        }
    }

}

