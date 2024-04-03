package kg.attractor.online_quiz_platform.dao;


import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<User> getUsers() {
        String sql = """
                    select * from USERS;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public Optional<User> getUserById(long id) {
        String sql = """
                select * from USERS
                where ID = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(User.class), id)
                ));
    }

    public void createUser(UserDto user) {
        String sql = """
                    insert into USERS(EMAIL, NAME, PASSWORD, ENABLED)
                    values(:email, :name, :password, :enabled);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("enabled", user.isEnabled());
        namedParameterJdbcTemplate.update(sql, params);
    }
}
