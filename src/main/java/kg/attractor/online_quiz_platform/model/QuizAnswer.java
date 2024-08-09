package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuizAnswer {
    private Long id;
    private Long userId;
    private Long quizId;
    private Long questionId;
    private Long optionId;
    private LocalDateTime timestamp;
}
