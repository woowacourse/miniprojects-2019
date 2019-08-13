package com.wootecobook.turkey.user.service.exception;

public class SignUpException extends RuntimeException {

    public static final String SIGN_UP_FAIL_MESSAGE = "회원가입에 실패했습니다.!";

    public SignUpException() {
        super(SIGN_UP_FAIL_MESSAGE);
    }

}
