package com.wootecobook.turkey.comment.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentAuthException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(CommentAuthException.class);
    private static final String DEFAULT_MESSAGE = "잘못된 접근입니다.";

    public CommentAuthException() {
        super(DEFAULT_MESSAGE);
        log.error(DEFAULT_MESSAGE);
    }
}
