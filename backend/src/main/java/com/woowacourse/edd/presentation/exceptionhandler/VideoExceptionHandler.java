package com.woowacourse.edd.presentation.exceptionhandler;

import com.woowacourse.edd.exceptions.InvalidContentsException;
import com.woowacourse.edd.exceptions.InvalidTitleException;
import com.woowacourse.edd.exceptions.InvalidYoutubeIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"com.woowacourse.edd.presentation.controller"})
public class VideoExceptionHandler {

    @ResponseBody
    @ExceptionHandler(InvalidTitleException.class)
    public ResponseEntity<Error> handleTitleError(InvalidTitleException e) {
        return new ResponseEntity<>(new Error("FAIL", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(InvalidYoutubeIdException.class)
    public ResponseEntity<Error> handleYoutubeIdError(InvalidYoutubeIdException e) {
        return new ResponseEntity<>(new Error("FAIL", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(InvalidContentsException.class)
    public ResponseEntity<Error> handleContentsError(InvalidContentsException e) {
        return new ResponseEntity<>(new Error("FAIL", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
