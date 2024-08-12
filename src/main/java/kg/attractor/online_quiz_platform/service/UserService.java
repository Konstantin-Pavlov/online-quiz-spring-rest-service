package kg.attractor.online_quiz_platform.service;



import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.exception.UserNotFoundException;

import java.util.List;


public interface UserService {
    List<UserDto> getUsers();
    UserDto getUserById(long id);
    void createUser(UserDto user);
}
