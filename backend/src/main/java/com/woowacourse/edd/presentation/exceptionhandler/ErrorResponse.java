package com.woowacourse.edd.presentation.exceptionhandler;

public class ErrorResponse {

    private final String result;
    private final String message;

    public ErrorResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
