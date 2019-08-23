package com.wootecobook.turkey.post.controller;

import com.wootecobook.turkey.commons.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice(basePackageClasses = {PostApiController.class})
public class PostApiControllerAdvice {

    @ExceptionHandler({BindException.class, EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(errorMessage);
    }

}
