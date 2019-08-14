package com.wootecobook.turkey.post.service.exception;

public class NotExistPostException extends RuntimeException {

    private static final String NOT_EXIST_POST_ERROR_MESSAGE = "존재하지 않는 포스트입니다.";

    public NotExistPostException() {
        super(NOT_EXIST_POST_ERROR_MESSAGE);
    }
}
