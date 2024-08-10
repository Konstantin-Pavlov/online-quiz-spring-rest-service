package kg.attractor.online_quiz_platform.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
    private Long id;
    @NotBlank(message = "question id is required")
    @Min(1)
    private Long questionId;
    @NotBlank(message = "option text is required")
    private String optionText;
    @NotNull(message = "Correct field is required")
    @AssertTrue(message = "Correct field must be true or false")
    private boolean correct;
}
