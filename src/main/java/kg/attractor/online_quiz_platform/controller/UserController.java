package kg.attractor.online_quiz_platform.controller;

import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.dto.UserStatisticsDto;
import kg.attractor.online_quiz_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("{userId}/statistics")
    public ResponseEntity<UserStatisticsDto> getUserStatistics(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUserStatisticsById(userId));
    }

}
