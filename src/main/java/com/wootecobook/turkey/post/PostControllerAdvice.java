package com.wootecobook.turkey.post;

import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.post.controller.api.PostApiController;
import com.wootecobook.turkey.post.service.exception.NotFoundPostException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {PostApiController.class})
public class PostControllerAdvice {

    @ExceptionHandler({BindException.class, NotFoundPostException.class})
    public ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(errorMessage);
    }

}
