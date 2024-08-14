package kg.attractor.online_quiz_platform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiniOptionDto {
    private Long id;
    @JsonProperty("answer_option")
    private String answerOption;
}
