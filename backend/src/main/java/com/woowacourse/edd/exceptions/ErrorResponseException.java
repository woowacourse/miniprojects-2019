package com.woowacourse.edd.exceptions;

public class ErrorResponseException extends RuntimeException {

    public ErrorResponseException(String message) {
        super(message);
    }
}
