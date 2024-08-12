package kg.attractor.online_quiz_platform.mapper;

import kg.attractor.online_quiz_platform.dto.QuizRateDto;
import kg.attractor.online_quiz_platform.model.QuizRate;

public class QuizRateMapper {
    public static QuizRateDto toQuizRateDto(QuizRate quizRate) {
        if (quizRate == null) {
            return null;
        }
        return QuizRateDto.builder()
                .id(quizRate.getId())
                .quizId(quizRate.getQuizId())
                .rate(quizRate.getRate())
                .build();
    }
}
