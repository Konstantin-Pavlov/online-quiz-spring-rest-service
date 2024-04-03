package kg.attractor.online_quiz_platform.service.impl;


import kg.attractor.online_quiz_platform.exception.ErrorResponseBody;
import kg.attractor.online_quiz_platform.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor

public class ErrorServiceImpl implements ErrorService {

    @Override
    public ErrorResponseBody makeResponse(Exception exception) {
        String message = exception.getMessage();
        log.error(Arrays.toString(exception.getStackTrace()));
        return ErrorResponseBody.builder()
                .title(message)
                .reasons(Map.of("errors", List.of(message)))
                .build();
    }

    @Override
    public ErrorResponseBody makeResponse(BindingResult exception) {
        Map<String, List<String>> reasons = new HashMap<>();
        log.error("not valid data - Validation error:");
        exception.getFieldErrors().stream()
                .filter(e -> e.getDefaultMessage() != null)
                .forEach(e -> {
                    log.error(e.getField() + ": " + e.getDefaultMessage());
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
