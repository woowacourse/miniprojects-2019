package techcourse.w3.woostagram.user.exception;

public class UserCreateException extends RuntimeException {
    private static final String ERROR_USER_CREATE = "이메일 또는 비밀번호 확인이 필요합니다.";

    public UserCreateException() {
        super(ERROR_USER_CREATE);
    }
}
