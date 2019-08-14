package com.woowacourse.edd.exceptions;

public class InvalidContentsException extends RuntimeException {

    private static final String INVALID_CONTENTS_MESSAGE = "잘못된 내용입니다.";

    public InvalidContentsException() {
        super(INVALID_CONTENTS_MESSAGE);
    }
}
