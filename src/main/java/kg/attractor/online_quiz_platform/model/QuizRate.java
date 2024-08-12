package kg.attractor.online_quiz_platform.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizRate {
    Long id;
    Long quizId;
    byte rate;
}
