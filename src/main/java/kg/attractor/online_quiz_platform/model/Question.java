package kg.attractor.online_quiz_platform.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Long id;
    private Long quizId;
    private String questionText;
    private List<Option> options;
}

