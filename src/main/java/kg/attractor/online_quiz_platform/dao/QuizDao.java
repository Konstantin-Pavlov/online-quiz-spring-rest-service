package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.dto.OptionDto;
import kg.attractor.online_quiz_platform.dto.Question;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.exception.QuizAlreadyExistsException;
import kg.attractor.online_quiz_platform.model.Quiz;
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
                WHERE TITLE = :title
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
        long generatedQuizId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"ID"});

        log.info("Saved quiz with title '{}'", quiz.getTitle());

        for (Question question : quiz.getQuestions()) {
            question.setQuizId(generatedQuizId);
            saveQuestion(question);
        }

    }

    private void saveQuestion(Question question) {
        String sql = """
                    INSERT INTO QUESTIONS (QUIZ_ID, QUESTION_TEXT)
                    VALUES (:quizId, :questionText);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("quizId", question.getQuizId())
                .addValue("questionText", question.getQuestionText());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"ID"});

        Long generatedQuestionId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        question.setId(generatedQuestionId); // Set the generated ID to the question

        log.info("Saved question with test '{}'", question.getQuestionText());

        for (OptionDto option : question.getOptions()) {
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

    private boolean quizExists(String quizTitle) {
        Optional<Quiz> quiz = getQuizByTitle(quizTitle);
        return quiz.isPresent();
    }

    public List<Quiz> getFullQuizzes() {
        String sql = """
                SELECT * FROM QUIZZES
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Quiz.class));
    }
}

