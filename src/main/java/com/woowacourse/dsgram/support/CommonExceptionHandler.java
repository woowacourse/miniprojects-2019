package com.woowacourse.dsgram.support;

import com.woowacourse.dsgram.service.exception.FileUploadException;
import com.woowacourse.dsgram.service.exception.JpaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({FileUploadException.class})
    public ResponseEntity<String> handle(FileUploadException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JpaException.class)
    public ResponseEntity<String> handle(JpaException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
