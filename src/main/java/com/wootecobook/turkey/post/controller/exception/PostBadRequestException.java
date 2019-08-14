package com.wootecobook.turkey.post.controller.exception;

public class PostBadRequestException extends RuntimeException {

    public PostBadRequestException(String message) {
        super(message);
    }

}
