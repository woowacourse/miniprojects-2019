package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends ErrorResponseException {

    public static final String COMMENT_NOT_FOUND_MESSAGE = "그런 댓글은 존재하지 않아!";

    public CommentNotFoundException() {
        super(COMMENT_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
