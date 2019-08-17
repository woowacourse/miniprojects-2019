package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.application.LoginService;
import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto signUpUser = loginService.save(userRequestDto);

        return new ResponseEntity<>(signUpUser, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> signIn(HttpSession httpSession,
                                                  @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto loginUser = loginService.login(userRequestDto);
        httpSession.setAttribute("loginUser", loginUser);

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }
}
