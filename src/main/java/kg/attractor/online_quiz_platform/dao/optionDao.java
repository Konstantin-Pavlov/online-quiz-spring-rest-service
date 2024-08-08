package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.dto.OptionDto;
import kg.attractor.online_quiz_platform.model.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OptionDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private QuizFullDao quizDao;

    public List<Option> getOptionsByQuestionId(long id) {
        String sql = """
                SELECT * FROM OPTIONS
                WHERE QUESTION_ID=?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Option.class), id);
    }

    public List<Option> getOptions() {
        String sql = """
                SELECT * FROM OPTIONS;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Option.class));
    }

    public void saveOption(OptionDto option) {
        quizDao.saveOption(option);
    }
}
