package techcourse.w3.woostagram.common.exception;

public class WoostagramExeception extends RuntimeException {

    public WoostagramExeception() {
    }

    public WoostagramExeception(String message) {
        super(message);
    }

    public WoostagramExeception(String message, Throwable cause) {
        super(message, cause);
    }

    public WoostagramExeception(Throwable cause) {
        super(cause);
    }

    public WoostagramExeception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
