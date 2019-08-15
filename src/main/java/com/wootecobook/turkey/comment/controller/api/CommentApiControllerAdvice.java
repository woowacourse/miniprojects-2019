package com.wootecobook.turkey.comment.controller.api;

import com.wootecobook.turkey.comment.domain.CommentAuthException;
import com.wootecobook.turkey.comment.service.exception.CommentSaveException;
import com.wootecobook.turkey.commons.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.ContextNotEmptyException;

@RestControllerAdvice(basePackageClasses = {CommentApiController.class})
public class CommentApiControllerAdvice {

    @ExceptionHandler({ContextNotEmptyException.class, CommentSaveException.class, CommentAuthException.class})
    private ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
