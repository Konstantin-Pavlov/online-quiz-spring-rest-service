package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.dto.OptionDto;
import kg.attractor.online_quiz_platform.dto.QuestionDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.exception.QuizAlreadyExistsException;
import kg.attractor.online_quiz_platform.exception.QuizNotFoundException;
import kg.attractor.online_quiz_platform.model.Quiz;
import kg.attractor.online_quiz_platform.model.QuizAnswer;
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
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuizDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<Quiz> getQuizByTitle(String title) {
        String sql = """
                SELECT * FROM QUIZZES
                WHERE TITLE = ?
                """;
        return Optional.ofNullable(DataAccessUtils.singleResult(
                template.query(sql, new BeanPropertyRowMapper<>(Quiz.class), title)
        ));
    }

    public void saveQuiz(QuizDto quiz) {
        if (quizExists(quiz.getTitle())) {
            throw new QuizAlreadyExistsException(
                    String.format("Quiz with title '%s' already exists", quiz.getTitle())
            );
        }
        String sql = """
                    INSERT INTO QUIZZES (TITLE, DESCRIPTION, CREATOR_ID)
                    VALUES (:title, :description, :creatorId);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", quiz.getTitle())
                .addValue("description", quiz.getDescription())
                .addValue("creatorId", quiz.getCreatorId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        // Explicitly checks for null and throws a more informative NullPointerException with a clear message if keyHolder.getKey() is null.
        namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"ID"});

        long generatedQuizId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        log.info("Saved quiz with title '{}'", quiz.getTitle());

        for (QuestionDto questionDto : quiz.getQuestions()) {
            questionDto.setQuizId(generatedQuizId);
            saveQuestion(questionDto);
        }

    }

    private void saveQuestion(QuestionDto questionDto) {
        String sql = """
                    INSERT INTO QUESTIONS (QUIZ_ID, QUESTION_TEXT)
                    VALUES (:quizId, :questionText);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("quizId", questionDto.getQuizId())
                .addValue("questionText", questionDto.getQuestionText());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"ID"});

        Long generatedQuestionId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        questionDto.setId(generatedQuestionId); // Set the generated ID to the question

        log.info("Saved question with test '{}'", questionDto.getQuestionText());

        for (OptionDto option : questionDto.getOptions()) {
            option.setQuestionId(generatedQuestionId); // Set the generated question ID to the options
            saveOption(option);
        }
    }

    public void saveOption(OptionDto option) {
        String sql = """
                    INSERT INTO OPTIONS ("QUESTION_ID", "OPTION_TEXT", CORRECT)
                    VALUES (:questionId, :optionText, :correct);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("questionId", option.getQuestionId())
                .addValue("optionText", option.getOptionText())
                .addValue("correct", option.isCorrect());
        namedParameterJdbcTemplate.update(sql, params);

        log.info("Saved option with option text '{}'", option.getOptionText());
    }

    public List<Quiz> getQuizzes() {
        String sql = """
                SELECT * FROM QUIZZES
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Quiz.class));
    }

    public Optional<Quiz> getQuizById(long id) {
        String sql = """
                SELECT * FROM QUIZZES
                WHERE ID = ?
                """;
        return Optional.ofNullable(DataAccessUtils.singleResult(
                template.query(sql, new BeanPropertyRowMapper<>(Quiz.class), id)
        ));
    }

    public void saveQuizAnswer(QuizAnswer userQuizAnswer) {
        String sql = """
                INSERT INTO USER_QUIZ_ANSWERS (USER_ID, QUIZ_ID, QUESTION_ID, OPTION_ID, TIMESTAMP)
                VALUES (:userId, :quizId, :questionId, :optionId, :timestamp)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userQuizAnswer.getUserId())
                .addValue("quizId", userQuizAnswer.getQuizId())
                .addValue("questionId", userQuizAnswer.getQuestionId())
                .addValue("optionId", userQuizAnswer.getOptionId())
                .addValue("timestamp", userQuizAnswer.getTimestamp());

        namedParameterJdbcTemplate.update(sql, params);
    }

    public int countQuestionsByQuizId(Long quizId) {
        String sql = """
                SELECT COUNT(*) FROM questions WHERE quiz_id = :quizId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("quizId", quizId);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        if (count == null) {
            throw new QuizNotFoundException("Quiz with ID " + quizId + " not found or has no questions.");
        }
        return count;
    }

    public Optional<QuizAnswer> findIfUserAnsweredQuiz(Long userId, Long quizId) {
        String sql = """
                SELECT * FROM USER_QUIZ_SUBMISSIONS
                WHERE user_id = ? AND quiz_id = ?
                """;

        List<QuizAnswer> results = template.query(sql, new Object[]{userId, quizId}, (rs, rowNum) -> {
            QuizAnswer quizAnswer = new QuizAnswer();
            quizAnswer.setId(rs.getLong("id"));
            quizAnswer.setUserId(rs.getLong("user_id"));
            quizAnswer.setQuizId(rs.getLong("quiz_id"));
            quizAnswer.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            return quizAnswer;
        });

        return results.stream().findFirst();
    }

    public void saveUserQuizSubmission(long userId, long quizId) {
        String sql = """
                INSERT INTO USER_QUIZ_SUBMISSIONS (USER_ID, QUIZ_ID)
                VALUES (:userId, :quizId )
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("quizId", quizId);

        namedParameterJdbcTemplate.update(sql, params);
    }

    public void saveQuizResult(long userId, long quizId, int score) {
        String sql = """
                INSERT INTO QUIZ_RESULTS (USER_ID, QUIZ_ID, SCORE)
                VALUES (:userId, :quizId, :score)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("quizId", quizId)
                .addValue("score", score);

        namedParameterJdbcTemplate.update(sql, params);
    }

    public int getCorrectAnswersCount(long userId, long quizId) {
        String sql = """
                SELECT SCORE FROM QUIZ_RESULTS
                WHERE USER_ID = :userId AND QUIZ_ID = :quizId
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("quizId", quizId);

        Integer score = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return score != null ? score : 0;
    }

    private boolean quizExists(String quizTitle) {
        Optional<Quiz> quiz = getQuizByTitle(quizTitle);
        return quiz.isPresent();
    }

}

