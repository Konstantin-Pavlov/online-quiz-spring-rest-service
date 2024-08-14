package kg.attractor.online_quiz_platform.service.impl;


import kg.attractor.online_quiz_platform.exception.EmailAlreadyExistsException;
import kg.attractor.online_quiz_platform.exception.ErrorResponseBody;
import kg.attractor.online_quiz_platform.exception.QuizAlreadyExistsException;
import kg.attractor.online_quiz_platform.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor

public class ErrorServiceImpl implements ErrorService {

    @Override
    public ErrorResponseBody makeResponse(Exception exception) {
        log.error(exception.getMessage());
//        exception.printStackTrace();
        return switch (exception) {
            case QuizAlreadyExistsException quizAlreadyExistsException -> ErrorResponseBody.builder()
                    .title("Quiz Already Exists")
                    .reasons(Map.of("error", List.of(exception.getMessage())))
                    .build();
            case EmailAlreadyExistsException emailAlreadyExistsException -> ErrorResponseBody.builder()
                    .title("Email Already Exists")
                    .reasons(Map.of("error", List.of(exception.getMessage())))
                    .build();
            case SQLSyntaxErrorException throwables -> ErrorResponseBody.builder()
                    .title("Database Syntax Error")
                    .reasons(Map.of("error", List.of(exception.getMessage())))
                    .build();
            case DuplicateKeyException duplicateKeyException -> ErrorResponseBody.builder()
                    .title("Duplicate Entry Error")
                    .reasons(Map.of("error", List.of("A record with the same key already exists in the database. Please check your input and try again.")))
                    .build();
            default ->

                // Handle other exceptions as needed
                    ErrorResponseBody.builder()
                            .title("Error")
                            .reasons(Map.of("error", List.of(exception.getMessage())))
                            .build();
        };

    }

    @Override
    public ErrorResponseBody makeResponse(BindingResult exception) {
        Map<String, List<String>> reasons = new HashMap<>();
        log.error("not valid data - Validation error:");
        exception.getFieldErrors().stream()
                .filter(e -> e.getDefaultMessage() != null)
                .forEach(e -> {
                    log.error("{}: {}", e.getField(), e.getDefaultMessage());
                    List<String> errors = new ArrayList<>();
                    errors.add(e.getDefaultMessage());
                    if (!reasons.containsKey(e.getField())) {
                        reasons.put(e.getField(), errors);
                    } else {
                        reasons.get(e.getField()).addAll(errors);
                    }
                });
        return ErrorResponseBody.builder()
                .title("Validation error")
                .reasons(reasons)
                .build();
    }

}
