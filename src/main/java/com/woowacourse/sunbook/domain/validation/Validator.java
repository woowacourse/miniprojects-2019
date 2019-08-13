package com.woowacourse.sunbook.domain.validation;

import com.woowacourse.sunbook.domain.validation.exception.InvalidValueException;

public class Validator {

    public static void checkValid(String target, String pattern, String message) {
        if (target.matches(pattern)) {
            return;
        }
        throw new InvalidValueException(message);
    }
}
