package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.response.LoginUserResponse;
import com.woowacourse.edd.application.response.SessionUser;
import com.woowacourse.edd.application.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    public static final String LOGIN_URL = "/v1/login";
    public static final String LOGOUT_URL = "/v1/logout";
    public static final String LOOKUP_URL = LOGIN_URL + "/users";

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(LOOKUP_URL)
    public ResponseEntity<LoginUserResponse> lookUp(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        LoginUserResponse loginUserResponse = loginService.lookUp(sessionUser.getId());
        return ResponseEntity.ok(loginUserResponse);
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
