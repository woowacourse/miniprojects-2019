package com.woowacourse.sunbook.application.exception;

public class NotFoundReactionException extends RuntimeException {
    private static final String NOT_FOUND_REACTION_MESSAGE = "존재하지 않는 리액션입니다.";

    public NotFoundReactionException() {
        super(NOT_FOUND_REACTION_MESSAGE);
    }
}
