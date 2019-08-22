package com.wootecobook.turkey.file.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FailedSaveFileException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(FailedSaveFileException.class);
    public static final String SAVE_FILE_FAILED_MESSAGE = "파일 저장에 실패했습니다";

    public FailedSaveFileException(final String message) {
        super(SAVE_FILE_FAILED_MESSAGE);
        log.error(message);
    }
}