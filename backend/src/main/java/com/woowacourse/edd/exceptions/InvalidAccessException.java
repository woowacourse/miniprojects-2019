package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidAccessException extends ErrorResponseException {

    public static final String INVALID_ACCESS_MESSAGE = "잘못된 접근입니다.";

    public InvalidAccessException() {
        super(INVALID_ACCESS_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
