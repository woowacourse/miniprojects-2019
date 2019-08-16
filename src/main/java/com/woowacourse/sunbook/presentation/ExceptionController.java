package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.application.exception.DuplicateEmailException;
import com.woowacourse.sunbook.domain.validation.exception.InvalidValueException;
import com.woowacourse.sunbook.support.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({DuplicateEmailException.class, InvalidValueException.class})
    @ResponseBody
    public ResponseEntity<ErrorMessage> duplicateEmailException(RuntimeException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.OK);
    }
}
