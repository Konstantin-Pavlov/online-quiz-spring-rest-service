package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Quiz {
    private Long id;
    private String title;
    private String description;
    private Long creatorId;
    private List<Question> questions;
}
