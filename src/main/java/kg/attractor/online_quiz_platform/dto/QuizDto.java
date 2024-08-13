package kg.attractor.online_quiz_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Long id;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "description is required")
    private String description;
    @Min(value = 1, message = "creator id must be 1 or more")
    private Long creatorId;
    @NotNull(message = "questionsNumber list is required")
    @Size(min = 2, message = "At least 2 questionsNumber are required")
    List<QuestionDto> questions;
}
