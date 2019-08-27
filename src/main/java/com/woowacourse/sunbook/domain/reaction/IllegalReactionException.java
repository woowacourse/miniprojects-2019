package com.woowacourse.sunbook.domain.reaction;

public class IllegalReactionException extends RuntimeException {
    private static final String ILLEGAL_REACTION_MESSAGE = "리액션을 할 수 없습니다.";

    public IllegalReactionException() {
        super(ILLEGAL_REACTION_MESSAGE);
    }
}
