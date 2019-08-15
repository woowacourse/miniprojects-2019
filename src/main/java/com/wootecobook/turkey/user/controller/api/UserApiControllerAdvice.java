package com.wootecobook.turkey.user.controller.api;

import com.wootecobook.turkey.user.service.exception.NotFoundUserException;
import com.wootecobook.turkey.user.service.exception.SignUpException;
import com.wootecobook.turkey.user.service.exception.UserDeleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserApiController.class)
public class UserApiControllerAdvice {

    @ExceptionHandler({SignUpException.class, UserDeleteException.class, NotFoundUserException.class})
    private ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
