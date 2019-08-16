package com.wootecobook.turkey.comment.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentSaveException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "해당 댓글을 저장할 수 없습니다.";

    public CommentSaveException(final String message) {
        super(DEFAULT_MESSAGE + message);
    }
}
