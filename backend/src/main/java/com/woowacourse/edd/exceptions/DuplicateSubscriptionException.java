package com.woowacourse.edd.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateSubscriptionException extends ErrorResponseException {

    public static final String DUPLICATE_SUBSCRIPTION_MESSAGE = "이미 구독중입니다.";

    public DuplicateSubscriptionException() {
        super(DUPLICATE_SUBSCRIPTION_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
