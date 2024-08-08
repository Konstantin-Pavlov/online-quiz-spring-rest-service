package kg.attractor.online_quiz_platform.exception;

public class QuizAlreadyExistsException extends RuntimeException {
    public QuizAlreadyExistsException(String message) {
        super(message);
    }
}
