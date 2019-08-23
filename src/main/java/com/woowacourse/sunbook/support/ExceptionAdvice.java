package com.woowacourse.sunbook.support;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.woowacourse.sunbook.application.exception.*;
import com.woowacourse.sunbook.domain.comment.exception.MismatchAuthException;
import com.woowacourse.sunbook.domain.validation.exception.InvalidValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({DuplicateEmailException.class, LoginException.class, InvalidValueException.class,
            NotFoundUserException.class, MismatchAuthException.class,
            NotFoundArticleException.class, NotFoundCommentException.class})
    public ResponseEntity<ErrorMessage> responseExceptionMessage(RuntimeException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidDefinitionException.class)
    public ResponseEntity<ErrorMessage> response(HttpMessageConversionException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getCause().getCause().getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.OK);
    }
}