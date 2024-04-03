package kg.attractor.online_quiz_platform.model;

import kg.attractor.online_quiz_platform.dto.QuestionFullDto;
import kg.attractor.online_quiz_platform.dto.QuizFullDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizFull {
    private Long id;
    private String title;
    private String description;
    private Long creatorId;
    private List<QuestionFullDto> questions;
}
