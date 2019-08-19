package com.woowacourse.edd.presentation.exceptionhandler;

import com.woowacourse.edd.exceptions.ErrorResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"com.woowacourse.edd.presentation.controller"})
public class VideoExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<ErrorResponse> handleErrorResponse(ErrorResponseException e) {
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorResponse("FAIL", e.getMessage()));
    }
}
