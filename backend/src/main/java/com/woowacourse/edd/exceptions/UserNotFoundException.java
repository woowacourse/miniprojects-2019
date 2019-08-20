package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ErrorResponseException {

    private static final String EXCEPTION_MESSAGE = "그런 유저는 존재하지 않아!";

    public UserNotFoundException() {
        super(EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
