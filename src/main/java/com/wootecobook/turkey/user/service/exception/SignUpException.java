package com.wootecobook.turkey.user.service.exception;

public class SignUpException extends RuntimeException {

    private static final String SIGN_UP_FAIL_MESSAGE = "Sign Up Fail Message : ";

    public SignUpException(String message) {
        super(SIGN_UP_FAIL_MESSAGE + message);
    }

}
