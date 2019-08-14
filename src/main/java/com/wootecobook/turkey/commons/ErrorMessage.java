package com.wootecobook.turkey.commons;

import lombok.Getter;

@Getter
public class ErrorMessage {

    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
