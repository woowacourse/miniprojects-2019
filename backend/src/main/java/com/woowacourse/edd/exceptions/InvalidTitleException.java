package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTitleException extends ErrorResponseException {

    private static final String INVALID_TITLE_MESSAGE = "제목은 한 글자 이상이어야합니다";

    public InvalidTitleException() {
        super(INVALID_TITLE_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
