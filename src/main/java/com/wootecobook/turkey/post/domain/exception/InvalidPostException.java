package com.wootecobook.turkey.post.domain.exception;

public class InvalidPostException extends RuntimeException {

    public InvalidPostException(final String message) {
        super(message);
    }

}
