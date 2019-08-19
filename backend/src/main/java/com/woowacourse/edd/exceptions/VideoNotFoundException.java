package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class VideoNotFoundException extends ErrorResponseException {

    private static final String EXCEPTION_MESSAGE = "그런 비디오는 존재하지 않아!";

    public VideoNotFoundException() {
        super(EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
