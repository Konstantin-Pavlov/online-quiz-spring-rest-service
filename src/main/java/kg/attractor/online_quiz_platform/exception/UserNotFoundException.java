package kg.attractor.online_quiz_platform.exception;

public class UserNotFoundException extends RuntimeException{
    public  UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
