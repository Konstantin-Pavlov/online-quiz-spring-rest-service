package kg.attractor.online_quiz_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    private int score;
    private List<CorrectAnswerDto> correctAnswersList;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CorrectAnswerDto {
        private Long questionId;
        private Long correctOptionId;
        private String questionContent;
        private String correctOptionContent;
    }
}
