package com.woowacourse.sunbook.presentation.excpetion;

import lombok.Getter;

@Getter
public class CustomException {
    private String errorMessage;

    public CustomException(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
