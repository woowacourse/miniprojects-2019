package com.woowacourse.edd.exceptions;

public class InvalidTitleException extends RuntimeException {

    private static final String INVALID_TITLE_MESSAGE = "잘못된 title입니다.";

    public InvalidTitleException(){
        super(INVALID_TITLE_MESSAGE);
    }
}
