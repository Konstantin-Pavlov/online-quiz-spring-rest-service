package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Option {
    private Long id;
    private Long questionId;
    private String optionText;
    private boolean correct;
}

