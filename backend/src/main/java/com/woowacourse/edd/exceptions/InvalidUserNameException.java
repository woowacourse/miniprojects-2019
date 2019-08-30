package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUserNameException extends ErrorResponseException {

    public static final String INVALID_USER_NAME_MESSAGE = "올바르지 않은 사용자 이름입니다.";

    public InvalidUserNameException() {
        super(INVALID_USER_NAME_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
