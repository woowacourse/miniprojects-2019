package techcourse.w3.woostagram.user.exception;

import techcourse.w3.woostagram.common.exception.WoostagramException;

public class UserProfileException extends WoostagramException {
    private static final String ERROR_USER_PROFILE = "파일이 제대로 선택되지 않았습니다.";

    public UserProfileException() {
        super(ERROR_USER_PROFILE);
    }
}
