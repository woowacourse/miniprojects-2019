package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends ErrorResponseException {

    public static final String PASSWORD_NOT_MATCH_MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordNotMatchException() {
        super(PASSWORD_NOT_MATCH_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
