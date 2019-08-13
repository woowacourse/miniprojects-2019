package com.wootecobook.turkey.post.service.exception;

public class NotExistPostException extends RuntimeException {
    public NotExistPostException(final String message) {
        super(message);
    }
}
