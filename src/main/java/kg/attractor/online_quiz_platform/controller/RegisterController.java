package kg.attractor.online_quiz_platform.controller;

import jakarta.validation.Valid;
import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.exception.EmailAlreadyExistsException;
import kg.attractor.online_quiz_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDto user) {
        try {
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("User added successfully");
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException("User with email '" + user.getEmail() + "' already exists.");
        }
    }

}
