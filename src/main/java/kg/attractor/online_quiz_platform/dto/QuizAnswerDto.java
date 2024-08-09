package kg.attractor.online_quiz_platform.dto;

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
    private Long userId;
    private Map<Long, Long> answers; // Map<QuestionId, OptionId>
}
