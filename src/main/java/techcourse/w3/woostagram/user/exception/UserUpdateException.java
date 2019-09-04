package techcourse.w3.woostagram.user.exception;

import techcourse.w3.woostagram.common.exception.WoostagramException;

public class UserUpdateException extends WoostagramException {
    public UserUpdateException(String message) {
        super(message);
    }
}
