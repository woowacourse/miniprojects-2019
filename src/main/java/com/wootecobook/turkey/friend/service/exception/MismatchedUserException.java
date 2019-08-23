package com.wootecobook.turkey.friend.service.exception;

public class MismatchedUserException extends RuntimeException {

    public MismatchedUserException(String message) {
        super(message);
    }
}
