package kg.attractor.online_quiz_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswerDto {
    private Long quizId;
    @Min(value = 1, message = "user id must be 1 or more")
    private Long userId;
    @NotNull(message = "Answers map is required")
    private Map<Long, Long> answers;
}
