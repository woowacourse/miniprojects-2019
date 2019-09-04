package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUserPasswordException extends ErrorResponseException {

    public static final String INVALID_USERPASSWORD_MESSAGE = "올바르지 않은 비밀번호 형식입니다.";

    public InvalidUserPasswordException() {
        super(INVALID_USERPASSWORD_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
