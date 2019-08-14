package com.wootecobook.turkey.comment.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentDeleteException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(CommentNotFoundException.class);
    public static final String DEFAULT_MESSAGE = "해당 댓글을 삭제할 수 없습니다.";

    public CommentDeleteException(final String message) {
        super(DEFAULT_MESSAGE);
        log.error(message);
    }
}
