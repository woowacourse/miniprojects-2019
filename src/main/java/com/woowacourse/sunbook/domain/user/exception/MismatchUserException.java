package com.woowacourse.sunbook.domain.user.exception;

public class MismatchUserException extends RuntimeException {
    private static final String MISMATCH_USER_EXCEPTION_MESSAGE = "본인이 아닙니다.";

    public MismatchUserException() {
        super(MISMATCH_USER_EXCEPTION_MESSAGE);
    }
}
