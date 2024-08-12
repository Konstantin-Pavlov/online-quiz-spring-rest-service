package kg.attractor.online_quiz_platform.exception;

public class UserAlreadyRatedQuizException extends RuntimeException {
    public UserAlreadyRatedQuizException(String message) {
        super(message);
    }
}