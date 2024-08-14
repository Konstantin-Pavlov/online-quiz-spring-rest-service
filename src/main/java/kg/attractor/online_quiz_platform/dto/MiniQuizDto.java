package kg.attractor.online_quiz_platform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MiniQuizDto {
    long id;
    String title;
    @JsonProperty("creator_id")
    Long creatorId;
    List<MiniQuestionDto> questions;
}
