package com.wootecobook.turkey.comment.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotCommentOwnerException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(NotCommentOwnerException.class);
    public static final String DEFAULT_MESSAGE = "잘못된 접근입니다.";

    public NotCommentOwnerException() {
        super(DEFAULT_MESSAGE);
        log.error(DEFAULT_MESSAGE);
    }
}
