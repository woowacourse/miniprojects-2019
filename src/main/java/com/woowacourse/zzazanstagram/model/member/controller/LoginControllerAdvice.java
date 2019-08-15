package com.woowacourse.zzazanstagram.model.member.controller;

import com.woowacourse.zzazanstagram.model.member.exception.MemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = LoginController.class)
public class LoginControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(LoginControllerAdvice.class);
    private static final String TAG = "[LoginControllerAdvice]";

    @ExceptionHandler(MemberException.class)
    public String handleIllegalUserParamsException(MemberException e) {
        log.error("{} MemberException >> {}", TAG, e.getMessage());

        return "redirect:/login";
    }
}
