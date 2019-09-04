package techcourse.w3.woostagram.user.exception;

import techcourse.w3.woostagram.common.exception.WoostagramException;

public class InvalidUserContentsException extends WoostagramException {
    public InvalidUserContentsException(String message) {
        super(message);
    }
}
