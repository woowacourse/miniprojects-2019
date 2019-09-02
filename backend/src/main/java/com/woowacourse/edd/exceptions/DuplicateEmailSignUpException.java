package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateEmailSignUpException extends ErrorResponseException {

    public static final String DUPLICATE_EMAIL_SIGNUP_MESSAGE = "이미 존재하는 이메일입니다(탈퇴한 이메일도 사용할 수 없습니다)";

    public DuplicateEmailSignUpException() {
        super(DUPLICATE_EMAIL_SIGNUP_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
