package com.wootecobook.turkey.user.controller.api;

import com.wootecobook.turkey.user.service.exception.SignUpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.wootecobook.turkey.user.controller.api")
public class UserApiControllerExceptionAdvice {

    @ExceptionHandler(SignUpException.class)
    private ResponseEntity<ErrorMessage> handleSignUpException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
