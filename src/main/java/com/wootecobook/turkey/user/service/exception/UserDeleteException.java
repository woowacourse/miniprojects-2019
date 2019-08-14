package com.wootecobook.turkey.user.service.exception;

public class UserDeleteException extends RuntimeException {

    public static final String USER_DELETE_FAIL_MESSAGE = "회원 탈퇴에 실패했습니다.!";

    public UserDeleteException() {
        super(USER_DELETE_FAIL_MESSAGE);
    }

}
