package com.woowacourse.sunbook.domain.comment.exception;

public class MismatchAuthException extends RuntimeException {
    private static final String MISMATCH_AUTH_EXCEPTION_MESSAGE = "권한이 없습니다.";

    public MismatchAuthException() {
        super(MISMATCH_AUTH_EXCEPTION_MESSAGE);
    }
}
