package com.woowacourse.edd.exceptions;

public class InvalidYoutubeIdException extends RuntimeException {

    private static final String INVALID_YOUTUBE_ID_MESSAGE = "유투브 아이디는 필수로 입력해야합니다.";

    public InvalidYoutubeIdException() {
        super(INVALID_YOUTUBE_ID_MESSAGE);
    }
}
