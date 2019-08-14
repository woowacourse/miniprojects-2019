package com.woowacourse.dsgram.service.exception;

public class JpaException extends RuntimeException {
    public JpaException(String message, Throwable cause) {
        super(message, cause);
    }
}
