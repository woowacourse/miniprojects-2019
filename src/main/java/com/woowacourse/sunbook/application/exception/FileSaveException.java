package com.woowacourse.sunbook.application.exception;

public class FileSaveException extends RuntimeException {
    private static final String FILE_SAVE_EXCEPTION = "저장에 실패했습니다.";

    public FileSaveException() {
        super(FILE_SAVE_EXCEPTION);
    }
}
