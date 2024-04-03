package kg.attractor.online_quiz_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDto {
    private Long id;
    private Long userId;
    private Long quizId;
}
