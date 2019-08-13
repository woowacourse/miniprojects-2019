package com.wootecobook.turkey.user.controller.api;

import lombok.Getter;

@Getter
public class ErrorMessage {

    private String errorMessage;

    public ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
