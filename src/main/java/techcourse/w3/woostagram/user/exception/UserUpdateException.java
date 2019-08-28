package techcourse.w3.woostagram.user.exception;

public class UserUpdateException extends RuntimeException {
    private static final String ERROR_USER_CREATE = "유저 네임은 반드시 필요합니다.";

    public UserUpdateException() {
        super(ERROR_USER_CREATE);
    }

    public UserUpdateException(String message) {
        super(message);
    }
}
