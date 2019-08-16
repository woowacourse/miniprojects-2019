package com.woowacourse.sunbook.support;

import lombok.Getter;

@Getter
public class ErrorMessage {
    private String errorMessage;

    public ErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
