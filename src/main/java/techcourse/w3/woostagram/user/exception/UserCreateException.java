package techcourse.w3.woostagram.user.exception;

import techcourse.w3.woostagram.common.exception.WoostagramException;

public class UserCreateException extends WoostagramException {
    public UserCreateException(String message) {
        super(message);
    }
}
