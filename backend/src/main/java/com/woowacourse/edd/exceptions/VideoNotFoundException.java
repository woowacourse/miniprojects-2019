package com.woowacourse.edd.exceptions;

public class VideoNotFoundException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "그런 비디오는 존재하지 않아!";

    public VideoNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
