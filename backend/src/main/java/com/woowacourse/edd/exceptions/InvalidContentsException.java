package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidContentsException extends ErrorResponseException {

    private static final String INVALID_CONTENTS_MESSAGE = "내용은 한 글자 이상이어야합니다";

    public InvalidContentsException() {
        super(INVALID_CONTENTS_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
