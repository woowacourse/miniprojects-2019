package com.wootecobook.turkey.comment.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentNotFoundException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(CommentNotFoundException.class);
    public static final String DEFAULT_MESSAGE = "해당 댓글을 찾을 수 없습니다.";

    public CommentNotFoundException(final Long id) {
        super(DEFAULT_MESSAGE + id);
        log.error("id : {}", id);
    }
}
