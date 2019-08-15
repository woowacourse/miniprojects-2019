package com.woowacourse.zzazanstagram.model.member.controller;

import com.woowacourse.zzazanstagram.model.member.exception.MemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = MemberController.class)
public class MemberControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(MemberControllerAdvice.class);
    private static final String TAG = "[MemberControllerAdvice]";

    @ExceptionHandler(MemberException.class)
    public String handleIllegalUserParamsException(MemberException e) {
        log.error("{} MemberException >> {}", TAG, e.getMessage());

        return "redirect:/signup";
    }
}
