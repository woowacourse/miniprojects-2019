package com.woowacourse.sunbook.support;

import lombok.Getter;

@Getter
public class CustomException {
    private String errorMessage;

    public CustomException(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
