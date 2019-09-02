package techcourse.w3.woostagram.user.exception;

import techcourse.w3.woostagram.common.exception.WoostagramExeception;

public class UserCreateException extends WoostagramExeception {
    public UserCreateException(String message) {
        super(message);
    }
}
