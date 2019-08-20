package techcourse.w3.woostagram.common.exception;

public class UnauthorizedException extends RuntimeException {
    private static final String ERROR_MISMATCH_USER = "권한이 없는 사용자입니다.";

    public UnauthorizedException() {
        super(ERROR_MISMATCH_USER);
    }
}
