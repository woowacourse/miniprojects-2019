package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthenticatedException extends ErrorResponseException {

    private static final String UNAUTHENTICATED_MESSAGE = "로그인이 필요합니다.";

    public UnauthenticatedException() {
        super(UNAUTHENTICATED_MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}
