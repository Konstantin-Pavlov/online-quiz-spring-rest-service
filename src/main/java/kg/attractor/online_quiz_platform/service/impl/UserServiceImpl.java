package kg.attractor.online_quiz_platform.service.impl;


import kg.attractor.online_quiz_platform.dao.UserDao;
import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.exception.UserNotFoundException;
import kg.attractor.online_quiz_platform.model.User;
import kg.attractor.online_quiz_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userDao.getUsers();
        List<UserDto> dtos = new ArrayList<>();
        users.forEach(user ->
                dtos.add(UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .password(user.getPassword())
                        .enabled(user.isEnabled())
                        .build()
                )
        );
        return dtos;
    }

    //    @SneakyThrows // try catch -> e.printStackTrace()
    @Override
    public UserDto getUserById(long id) throws UserNotFoundException {
        User user = userDao.getUserById(id).orElseThrow(
                () -> new UserNotFoundException("Can't find user with email: " + id)
        );
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .build();
    }

    @Override
    public void createUser(UserDto user) {
        userDao.createUser(user);
        log.info("added user with email " + user.getEmail());
    }
}
