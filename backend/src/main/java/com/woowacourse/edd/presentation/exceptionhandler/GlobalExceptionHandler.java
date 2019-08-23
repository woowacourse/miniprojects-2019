package com.woowacourse.edd.presentation.exceptionhandler;

import com.woowacourse.edd.exceptions.ErrorResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"com.woowacourse.edd.presentation.controller"})
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<ErrorResponse> handleErrorResponse(ErrorResponseException e) {
        return ResponseEntity.status(e.getStatus())
            .body(new ErrorResponse("FAIL", e.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("FAIL", e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }
}
