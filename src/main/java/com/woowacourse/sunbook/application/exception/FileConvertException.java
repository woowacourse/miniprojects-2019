package com.woowacourse.sunbook.application.exception;

public class FileConvertException extends RuntimeException {
    private static final String FILE_CONVERT_EXCEPTION_MESSAGE = "MultipartFile -> File로 전환이 실패했습니다.";

    public FileConvertException() {
        super(FILE_CONVERT_EXCEPTION_MESSAGE);
    }
}
