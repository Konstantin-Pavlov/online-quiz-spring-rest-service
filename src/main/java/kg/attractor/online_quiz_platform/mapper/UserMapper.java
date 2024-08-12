package kg.attractor.online_quiz_platform.mapper;

import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.model.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .build();
    }

    public static User fromDto(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .enabled(userDto.isEnabled())
                .build();
    }
}