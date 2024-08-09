package kg.attractor.online_quiz_platform.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    private Long id;
    private Long questionId;
    private String optionText;
    private boolean correct;
}

