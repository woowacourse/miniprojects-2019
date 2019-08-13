package com.wootecobook.turkey.user.service.exception;

public class NotFoundUserException extends RuntimeException {
    public static final String NOT_FOUND_USER_MESSAGE = "유저를 찾을수 없습니다.";

    public NotFoundUserException() {
        super(NOT_FOUND_USER_MESSAGE);
    }
}
