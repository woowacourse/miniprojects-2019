package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    public static final String LOGIN_URL = "/v1/login";
    public static final String LOGOUT_URL = "/v1/logout";

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(LOGIN_URL)
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        session.setAttribute("user", loginService.login(loginRequestDto));
        return ResponseEntity.ok().build();
    }

    @PostMapping(LOGOUT_URL)
    public ResponseEntity logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
