package techcourse.w3.woostagram.article.exception;

import techcourse.w3.woostagram.common.exception.WoostagramException;

public class RequestTooFastException extends WoostagramException {
    private static final String ERROR_TOO_FAST = "요청이 너무 잦습니다. 잠시 후 시도해주세요.";

    public RequestTooFastException() {
        super(ERROR_TOO_FAST);
    }
}
