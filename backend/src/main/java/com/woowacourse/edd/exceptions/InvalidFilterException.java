package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidFilterException extends ErrorResponseException {

    private static final String MESSAGE = "지원되지 않는 필터입니다";

    public InvalidFilterException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
