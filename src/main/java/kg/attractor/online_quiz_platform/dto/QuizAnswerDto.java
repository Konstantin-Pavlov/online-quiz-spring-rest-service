package kg.attractor.online_quiz_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "quiz id is required")
    @Min(1)
    private Long quizId;
    @NotBlank(message = "user id is required")
    @Min(1)
    private Long userId;
    @NotNull(message = "Answers map is required")
    private Map<Long, Long> answers;
}
