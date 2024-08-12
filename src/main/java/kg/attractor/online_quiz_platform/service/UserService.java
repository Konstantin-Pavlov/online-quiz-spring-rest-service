package kg.attractor.online_quiz_platform.service;


import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.dto.UserStatisticsDto;

import java.util.List;


public interface UserService {
    List<UserDto> getUsers();

    UserDto getUserById(long id);

    void createUser(UserDto user);

    UserStatisticsDto getUserStatisticsById(long userId);
}
