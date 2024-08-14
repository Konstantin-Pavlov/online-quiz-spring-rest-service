package kg.attractor.online_quiz_platform.mapper;

import kg.attractor.online_quiz_platform.dto.MiniOptionDto;
import kg.attractor.online_quiz_platform.dto.MiniQuestionDto;
import kg.attractor.online_quiz_platform.dto.OptionDto;
import kg.attractor.online_quiz_platform.dto.QuestionDto;
import kg.attractor.online_quiz_platform.model.Option;
import kg.attractor.online_quiz_platform.model.Question;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static QuestionDto toDto(Question question) {
        if (question == null) {
            return null;
        }

        List<OptionDto> optionsDto = question.getOptions().stream()
                .map(OptionMapper::toDto)
                .collect(Collectors.toList());

        return QuestionDto.builder()
                .id(question.getId())
                .quizId(question.getQuizId())
                .questionText(question.getQuestionText())
                .options(optionsDto)
                .build();
    }

    public static MiniQuestionDto toMiniDto(Question question) {
        if (question == null) {
            return null;
        }

        List<MiniOptionDto> miniOptionDtos = question.getOptions().stream()
                .map(OptionMapper::toMiniDto)
                .toList();

        return MiniQuestionDto.builder()
                .id(question.getId())
                .title(question.getQuestionText())
                .options(miniOptionDtos)
                .build();
    }

    public static Question fromDto(QuestionDto questionDto) {
        if (questionDto == null) {
            return null;
        }

        List<Option> options = questionDto.getOptions().stream()
                .map(OptionMapper::fromDto)
                .collect(Collectors.toList());

        return Question.builder()
                .id(questionDto.getId())
                .quizId(questionDto.getQuizId())
                .questionText(questionDto.getQuestionText())
                .options(options)
                .build();
    }
}