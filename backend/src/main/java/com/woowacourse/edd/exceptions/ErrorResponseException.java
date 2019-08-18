package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorResponseException extends RuntimeException {

    private final HttpStatus status;

    public ErrorResponseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
