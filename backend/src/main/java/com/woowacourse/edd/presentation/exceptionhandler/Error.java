package com.woowacourse.edd.presentation.exceptionhandler;

public class Error {

    private String result;
    private String message;

    private Error() {
    }

    public Error(String result, String message) {
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
