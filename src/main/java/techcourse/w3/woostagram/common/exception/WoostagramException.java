package techcourse.w3.woostagram.common.exception;

public class WoostagramException extends RuntimeException {

    public WoostagramException() {
    }

    public WoostagramException(String message) {
        super(message);
    }

    public WoostagramException(String message, Throwable cause) {
        super(message, cause);
    }

    public WoostagramException(Throwable cause) {
        super(cause);
    }

    public WoostagramException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
