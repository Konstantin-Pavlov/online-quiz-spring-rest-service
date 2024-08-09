package kg.attractor.online_quiz_platform.exception.handler;

import kg.attractor.online_quiz_platform.exception.EmailAlreadyExistsException;
import kg.attractor.online_quiz_platform.exception.ErrorResponseBody;
import kg.attractor.online_quiz_platform.exception.QuizAlreadyExistsException;
import kg.attractor.online_quiz_platform.exception.QuizNotFoundException;
import kg.attractor.online_quiz_platform.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ErrorService errorService;

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> noSuchElement(QuizNotFoundException exception) {
        return new ResponseEntity<>(errorService.makeResponse(exception), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseBody> validationHandler(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(errorService.makeResponse(exception.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuizAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseBody> quizAddingError(QuizAlreadyExistsException exception) {
        return new ResponseEntity<>(errorService.makeResponse(exception), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseBody> handleEmailAlreadyExists(EmailAlreadyExistsException exception) {
        return new ResponseEntity<>(errorService.makeResponse(exception), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<ErrorResponseBody> handleSQLSyntaxError(SQLSyntaxErrorException exception) {
        return new ResponseEntity<>(errorService.makeResponse(exception), HttpStatus.BAD_REQUEST);
    }
}