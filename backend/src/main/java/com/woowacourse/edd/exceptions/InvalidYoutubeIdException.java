package com.woowacourse.edd.exceptions;

public class InvalidYoutubeIdException extends RuntimeException{

    private static String INVALID_YOUTUBE_ID_MESSAGE = "잘못된 주소입니다.";

    public InvalidYoutubeIdException() {
        super(INVALID_YOUTUBE_ID_MESSAGE);
    }
}
