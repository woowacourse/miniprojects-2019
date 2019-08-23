package com.woowacourse.sunbook.application.exception;

public class NotFoundUserException extends RuntimeException {
    private static final String NOT_FOUND_USER_EXCEPTION_MESSAGE = "찾을 수 없는 유저입니다.";

    public NotFoundUserException() {
        super(NOT_FOUND_USER_EXCEPTION_MESSAGE);
    }
}
