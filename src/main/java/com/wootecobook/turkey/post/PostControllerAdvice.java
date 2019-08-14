package com.wootecobook.turkey.post;

import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.post.controller.PostApiController;
import com.wootecobook.turkey.post.controller.exception.PostBadRequestException;
import com.wootecobook.turkey.post.service.exception.NotExistPostException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackageClasses = {PostApiController.class})
public class PostControllerAdvice {

    @ExceptionHandler({PostBadRequestException.class, NotExistPostException.class})
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
