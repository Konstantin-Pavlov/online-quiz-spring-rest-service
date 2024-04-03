package kg.attractor.online_quiz_platform.dto;

import kg.attractor.online_quiz_platform.model.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionFullDto {
    private Long id;
    private Long quizId;
    private String questionText;
    private List<OptionDto> options;
}
