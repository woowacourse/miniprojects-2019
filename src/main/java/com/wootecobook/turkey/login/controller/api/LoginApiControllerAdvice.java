package com.wootecobook.turkey.login.controller.api;

import com.wootecobook.turkey.login.service.exception.LoginFailException;
import com.wootecobook.turkey.user.controller.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = LoginApiController.class)
public class LoginApiControllerAdvice {

    @ExceptionHandler({LoginFailException.class})
    private ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
