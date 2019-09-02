package techcourse.w3.woostagram.user.exception;

import techcourse.w3.woostagram.common.exception.WoostagramException;

public class LoginException extends WoostagramException {
    private static final String ERROR_LOGIN = "아이디 또는 비밀번호를 다시 확인하세요.";

    public LoginException() {
        super(ERROR_LOGIN);
    }
}
