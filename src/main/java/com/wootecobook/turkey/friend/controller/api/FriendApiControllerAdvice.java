package com.wootecobook.turkey.friend.controller.api;

import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.commons.exception.NotLoginException;
import com.wootecobook.turkey.friend.service.exception.FriendAskFailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice(basePackageClasses = FriendApiController.class)
public class FriendApiControllerAdvice {

    @ExceptionHandler({EntityNotFoundException.class, NotLoginException.class, FriendAskFailException.class})
    private ResponseEntity<ErrorMessage> handleException(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }

}
