package com.woowacourse.sunbook.application.exception;
public class DuplicateEmailException extends RuntimeException {
    private static final String DUPLICATE_EMAIL_EXCEPTION_MESSAGE = "중복된 이메일입니다.";

    public DuplicateEmailException() {
        super(DUPLICATE_EMAIL_EXCEPTION_MESSAGE);
    }
}
