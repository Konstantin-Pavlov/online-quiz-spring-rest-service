package kg.attractor.online_quiz_platform.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserStatisticsDto {
    long userId;
    String name;
    String email;
    long numberOfCompletedQuizzes;
    long maxScore;
    long minScore;
    double averageScore;
}
