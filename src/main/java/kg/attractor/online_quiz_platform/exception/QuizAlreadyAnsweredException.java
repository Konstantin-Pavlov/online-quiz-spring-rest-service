package kg.attractor.online_quiz_platform.exception;

public class QuizAlreadyAnsweredException extends RuntimeException {
    public QuizAlreadyAnsweredException(String message) {
        super(message);
    }
}
