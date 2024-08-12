package kg.attractor.online_quiz_platform.service.impl;


import kg.attractor.online_quiz_platform.dao.UserDao;
import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.dto.UserStatisticsDto;
import kg.attractor.online_quiz_platform.exception.UserNotFoundException;
import kg.attractor.online_quiz_platform.mapper.UserMapper;
import kg.attractor.online_quiz_platform.model.QuizResult;
import kg.attractor.online_quiz_platform.model.User;
import kg.attractor.online_quiz_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userDao.getUsers();
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(long userId) {
        User user = userDao.getUserById(userId).orElseThrow(
                () -> new UserNotFoundException("Can't find user with id: " + userId)
        );
        return UserMapper.toDto(user);
    }

    @Override
    public void createUser(UserDto user) {
        userDao.createUser(user);
        log.info("added user with email {}", user.getEmail());
    }

    @Override
    public UserStatisticsDto getUserStatisticsById(long userId) {
        User user = UserMapper.fromDto(getUserById(userId));
        List<QuizResult> quizResults = userDao.getQuizResultsByUserId(userId);

        return UserStatisticsDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .numberOfCompletedQuizzes(quizResults.size())
                .maxScore(quizResults.stream()
                        .mapToLong(QuizResult::getScore)
                        .max().orElse(Long.MIN_VALUE))
                .minScore(quizResults.stream()
                        .mapToLong(QuizResult::getScore)
                        .min().orElse(Long.MAX_VALUE))
                .averageScore(quizResults.stream()
                        .mapToDouble(QuizResult::getScore)
                        .average().orElse(Double.MIN_VALUE))
                .build();
    }

}
