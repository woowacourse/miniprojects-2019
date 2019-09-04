package com.wootecobook.turkey.login.controller;

import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.commons.exception.NotLoginException;
import com.wootecobook.turkey.login.service.exception.LoginFailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = LoginApiController.class)
public class LoginApiControllerAdvice {

    @ExceptionHandler({LoginFailException.class, NotLoginException.class})
    protected ResponseEntity<ErrorMessage> handleException(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
