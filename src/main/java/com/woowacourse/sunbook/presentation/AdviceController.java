package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.application.exception.DuplicateEmailException;
import com.woowacourse.sunbook.domain.validation.exception.InvalidValueException;
import com.woowacourse.sunbook.presentation.excpetion.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AdviceController {
    @ExceptionHandler({DuplicateEmailException.class, InvalidValueException.class})
    @ResponseBody
    public ResponseEntity<CustomException> duplicateEmailException(RuntimeException exception) {
        CustomException customException = new CustomException(exception.getMessage());
        return new ResponseEntity<>(customException, HttpStatus.OK);
    }
}
