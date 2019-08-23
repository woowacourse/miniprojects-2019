package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends ErrorResponseException {

    public static final String UNAUTHORIZED_ACCESS_MESSAGE = "권한이 없습니다.";

    public UnauthorizedAccessException() {
        super(UNAUTHORIZED_ACCESS_MESSAGE, HttpStatus.FORBIDDEN);
    }
}
