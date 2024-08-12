package kg.attractor.online_quiz_platform.dao;


import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.model.Quiz;
import kg.attractor.online_quiz_platform.model.QuizResult;
import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final Map<String, Integer> userAuthorityMap = new HashMap<>() {{
        put("ADMIN", 1);
        put("GUEST", 2);
        put("MANAGER", 3);
        put("USER", 4);
        put("SUPERUSER", 5);
    }};

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

    public List<QuizResult> getQuizResultsByUserId(long userId) {
        String sql = """
                SELECT * FROM QUIZ_RESULTS
                WHERE USER_ID = :userId
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(QuizResult.class));
    }

    public void createUser(UserDto user) {
        String sql = """
                    insert into USERS(EMAIL, NAME, PASSWORD, ENABLED)
                    values(:email, :name, :password, :enabled);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("name", user.getName())
                .addValue("password", encoder.encode(user.getPassword()))
                .addValue("enabled", true);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"ID"});

        long generatedUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        Optional<User> userById = getUserById(generatedUserId);
        if (userById.isPresent()) {
            // USER role by default
            setUserRole(userById.get().getId(), userAuthorityMap.get("GUEST"));
            log.info("role 'GUEST' set for user with email '{}'", user.getEmail());
        } else {
            log.error("Can't set user role - user with email '{}' not found.", user.getEmail());
        }
    }

    private void setUserRole(long userId, Integer authorityId) {
        String sql = """
                insert into USER_AUTHORITY(USER_ID, AUTHORITY_ID)
                values (:userId, :authorityId);
                """;

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("authorityId", authorityId)
        );
    }
}
