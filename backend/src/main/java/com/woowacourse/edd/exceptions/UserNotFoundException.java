package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ErrorResponseException {

    public static final String USER_NOT_FOUND_MESSAGE = "그런 유저는 존재하지 않아!";

    public UserNotFoundException() {
        super(USER_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
