package techcourse.w3.woostagram.user.exception;

public class InvalidUserContentsException extends IllegalArgumentException {
    public InvalidUserContentsException(String message) {
        super(message);
    }
}
