package kg.attractor.online_quiz_platform.model;

import kg.attractor.online_quiz_platform.dto.OptionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionWithOptions {
    private Long id;
    private Long quizId;
    private String questionText;
    private List<OptionDto> options;
}
