package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidYoutubeIdException extends ErrorResponseException {

    private static final String INVALID_YOUTUBE_ID_MESSAGE = "유투브 아이디는 필수로 입력해야합니다.";

    public InvalidYoutubeIdException() {
        super(INVALID_YOUTUBE_ID_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
