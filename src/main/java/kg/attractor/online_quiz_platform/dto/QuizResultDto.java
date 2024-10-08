package kg.attractor.online_quiz_platform.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResultDto {
    Long id;
    Long userId;
    Long quizId;
    int score;
    int totalQuestionsNumber;
    int correctAnswers;
    int wrongAnswers;



}
