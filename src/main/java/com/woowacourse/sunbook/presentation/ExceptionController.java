package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.application.exception.DuplicateEmailException;
import com.woowacourse.sunbook.application.exception.LoginException;
import com.woowacourse.sunbook.domain.validation.exception.InvalidValueException;
import com.woowacourse.sunbook.support.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({DuplicateEmailException.class, LoginException.class, InvalidValueException.class})
    public ResponseEntity<ErrorMessage> responseExceptionMessage(RuntimeException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.OK);
    }
}
