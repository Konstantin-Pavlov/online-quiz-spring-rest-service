package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dto.OptionDto;

import java.util.List;

public interface OptionService {
    List<OptionDto> getOptionsByQuestionId(long id);

    List<OptionDto> getOptions();
}
