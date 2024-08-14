package kg.attractor.online_quiz_platform.mapper;

import kg.attractor.online_quiz_platform.dto.MiniQuestionDto;
import kg.attractor.online_quiz_platform.dto.MiniQuizDto;
import kg.attractor.online_quiz_platform.dto.QuestionDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.model.Quiz;

import java.util.List;

public class QuizMapper {

    public static QuizDto toDto(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        List<QuestionDto> questionsDto = quiz.getQuestions().stream()
                .map(QuestionMapper::toDto)
                .toList();

        return QuizDto.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .creatorId(quiz.getCreatorId())
                .questions(questionsDto)
                .build();
    }

    public static MiniQuizDto toMiniDto(Quiz quiz) {
        if (quiz == null) {
            return null;
        }
        List<MiniQuestionDto> miniQuestionDtos = quiz.getQuestions().stream()
                .map(QuestionMapper::toMiniDto)
                .toList();

        return MiniQuizDto.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .creatorId(quiz.getCreatorId())
                .questions(miniQuestionDtos)
                .build();
    }
}