package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUserEmailException extends ErrorResponseException {

    public static final String INVALID_USERMEAIL_MESSAGE = "올바르지 않은 이메일 입니다.";

    public InvalidUserEmailException() {
        super(INVALID_USERMEAIL_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
