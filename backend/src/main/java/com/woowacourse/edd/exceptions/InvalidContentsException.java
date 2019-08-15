package com.woowacourse.edd.exceptions;

public class InvalidContentsException extends RuntimeException {

    private static final String INVALID_CONTENTS_MESSAGE = "내용은 한 글자 이상이어야합니다";

    public InvalidContentsException() {
        super(INVALID_CONTENTS_MESSAGE);
    }
}
