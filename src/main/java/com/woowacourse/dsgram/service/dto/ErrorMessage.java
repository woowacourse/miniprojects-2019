package com.woowacourse.dsgram.service.dto;

import lombok.Getter;

@Getter
public class ErrorMessage {
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
