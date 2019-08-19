package com.woowacourse.sunbook.application.exception;

public class LoginException extends RuntimeException{
    private static final String LOGIN_EXCEPTION_MESSAGE = "로그인 실패";

    public LoginException() {
        super(LOGIN_EXCEPTION_MESSAGE);
    }
}
