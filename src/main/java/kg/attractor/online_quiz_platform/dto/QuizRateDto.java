package kg.attractor.online_quiz_platform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizRateDto {
    Long id;
    Long quizId;
    @Min(value = 1, message = " rate should be minimum 1")
    @Max(value = 5, message = " rate should be maximum 5")
    byte rate;
}
