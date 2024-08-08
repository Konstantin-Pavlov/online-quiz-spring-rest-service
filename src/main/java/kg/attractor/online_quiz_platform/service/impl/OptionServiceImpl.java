package kg.attractor.online_quiz_platform.service.impl;

import kg.attractor.online_quiz_platform.dao.OptionDao;
import kg.attractor.online_quiz_platform.dto.OptionDto;
import kg.attractor.online_quiz_platform.model.Option;
import kg.attractor.online_quiz_platform.service.OptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {
    private final OptionDao optionDao;

    @Override
    public List<OptionDto> getOptionsByQuestionId(long id) {
        List<Option> options = optionDao.getOptionsByQuestionId(id);
        List<OptionDto> dtos = new ArrayList<>();
        options.forEach(option ->
                dtos.add(OptionDto.builder()
                        .id(option.getId())
                        .questionId(option.getQuestionId())
                        .optionText(option.getOptionText())
                        .correct(option.isCorrect())
                        .build()
                )
        );
        return dtos;
    }

    @Override
    public List<OptionDto> getOptions() {
        List<Option> options = optionDao.getOptions();
        List<OptionDto> dtos = new ArrayList<>();
        options.forEach(option ->
                dtos.add(OptionDto.builder()
                        .id(option.getId())
                        .questionId(option.getQuestionId())
                        .optionText(option.getOptionText())
                        .correct(option.isCorrect())
                        .build()
                )
        );
        return dtos;
    }
}
