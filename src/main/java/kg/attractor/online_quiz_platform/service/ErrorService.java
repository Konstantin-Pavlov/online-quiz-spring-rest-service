package kg.attractor.online_quiz_platform.service;


import kg.attractor.online_quiz_platform.exception.ErrorResponseBody;
import org.springframework.validation.BindingResult;

public interface ErrorService {
    ErrorResponseBody makeResponse(Exception exception);

    ErrorResponseBody makeResponse(BindingResult exception);
}
