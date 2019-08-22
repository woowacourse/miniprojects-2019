package techcourse.w3.woostagram.common.exception;

public class UnAuthorizedException extends RuntimeException {
    private static final String ERROR_MISMATCH_USER = "권한이 없는 사용자입니다.";

    public UnAuthorizedException() {
        super(ERROR_MISMATCH_USER);
    }
}
