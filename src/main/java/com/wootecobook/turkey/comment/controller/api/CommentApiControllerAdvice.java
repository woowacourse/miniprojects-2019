package com.wootecobook.turkey.comment.controller.api;

import com.wootecobook.turkey.user.controller.api.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {CommentApiController.class})
public class CommentApiControllerAdvice {

    @ExceptionHandler({RuntimeException.class})
    private ResponseEntity<ErrorMessage> handleException(Exception e) {
        //TODO 합치면 commons 패키지로?
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
