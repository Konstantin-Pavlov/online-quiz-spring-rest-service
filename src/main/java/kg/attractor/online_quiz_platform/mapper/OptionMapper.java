package kg.attractor.online_quiz_platform.mapper;

import kg.attractor.online_quiz_platform.dto.MiniOptionDto;
import kg.attractor.online_quiz_platform.dto.OptionDto;
import kg.attractor.online_quiz_platform.model.Option;

public class OptionMapper {

    public static OptionDto toDto(Option option) {
        if (option == null) {
            return null;
        }

        return OptionDto.builder()
                .id(option.getId())
                .questionId(option.getQuestionId())
                .optionText(option.getOptionText())
                .correct(option.isCorrect())
                .build();
    }

    public static MiniOptionDto toMiniDto(Option option) {
        if (option == null) {
            return null;
        }
        return MiniOptionDto.builder()
                .id(option.getId())
                .answerOption(option.getOptionText())
                .build();
    }

    public static Option fromDto(OptionDto optionDto) {
        if (optionDto == null) {
            return null;
        }

        return Option.builder()
                .id(optionDto.getId())
                .questionId(optionDto.getQuestionId())
                .optionText(optionDto.getOptionText())
                .correct(optionDto.isCorrect())
                .build();

    }
}