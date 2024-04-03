package kg.attractor.online_quiz_platform.exception.handler;


import kg.attractor.online_quiz_platform.exception.ErrorResponseBody;
import kg.attractor.online_quiz_platform.exception.MovieNotFoundException;
import kg.attractor.online_quiz_platform.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ErrorService errorService;

    //    @ExceptionHandler(NoSuchElementException.class)
    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponseBody>/* ErrorResponse*/ noSuchElement(MovieNotFoundException exception) {
//        return ErrorResponse.builder(exception, HttpStatus.NOT_FOUND, exception.getMessage()).build();
        return new ResponseEntity<>(errorService.makeResponse(exception), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public /*ErrorRespons*/ ResponseEntity<ErrorResponseBody> validationHandler(MethodArgumentNotValidException exception) {
//        return ErrorResponse.builder(exception, HttpStatus.BAD_REQUEST, exception.getMessage()).build();
        return new ResponseEntity<>(errorService.makeResponse(exception.getBindingResult()), HttpStatus.BAD_REQUEST);
    }
}
