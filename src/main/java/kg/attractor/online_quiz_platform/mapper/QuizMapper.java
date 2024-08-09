package kg.attractor.online_quiz_platform.mapper;

import kg.attractor.online_quiz_platform.dto.QuestionDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.model.Quiz;

import java.util.List;
import java.util.stream.Collectors;

public class QuizMapper {

    public static QuizDto toDto(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        List<QuestionDto> questionsDto = quiz.getQuestions().stream()
                .map(QuestionMapper::toDto)
                .collect(Collectors.toList());

        return QuizDto.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .creatorId(quiz.getCreatorId())
                .questions(questionsDto)
                .build();
    }
}