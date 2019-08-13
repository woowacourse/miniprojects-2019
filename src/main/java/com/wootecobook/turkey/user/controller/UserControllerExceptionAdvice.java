package com.wootecobook.turkey.user.controller;

import com.wootecobook.turkey.user.service.exception.SignUpException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice(basePackages = "com.wootecobook.turkey.user.controller")
public class UserControllerExceptionAdvice {

    @ExceptionHandler(SignUpException.class)
    private RedirectView handleSignUpException() {
        return new RedirectView("/users/form");
    }

}
