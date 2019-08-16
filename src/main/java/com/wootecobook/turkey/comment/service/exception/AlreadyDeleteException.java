package com.wootecobook.turkey.comment.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlreadyDeleteException extends RuntimeException{
    private static final Logger log = LoggerFactory.getLogger(CommentNotFoundException.class);
    public static final String DEFAULT_MESSAGE = "해당 댓글은 이미 삭제되었습니다.";

    public AlreadyDeleteException(final Long id) {
        super(DEFAULT_MESSAGE + id);
        log.error("id : {}", id);
    }
}
