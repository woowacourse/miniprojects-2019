package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUserNameException extends ErrorResponseException {

    public static final String INVALID_USER_NAME_MESSAGE = "이름은 영어 또는 한글만 가능합니다.";

    public InvalidUserNameException() {
        super(INVALID_USER_NAME_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
