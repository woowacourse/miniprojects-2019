package techcourse.w3.woostagram.user.exception;

import techcourse.w3.woostagram.common.exception.WoostagramExeception;

public class InvalidUserContentsException extends WoostagramExeception {
    public InvalidUserContentsException(String message) {
        super(message);
    }
}
