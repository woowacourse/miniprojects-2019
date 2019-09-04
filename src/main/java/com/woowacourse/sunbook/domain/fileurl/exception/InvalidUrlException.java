package com.woowacourse.sunbook.domain.fileurl.exception;

public class InvalidUrlException extends RuntimeException {
    private static final String INVALID_URL_MESSAGE = "권한이 없습니다.";

    public InvalidUrlException() {
        super(INVALID_URL_MESSAGE);
    }
}
