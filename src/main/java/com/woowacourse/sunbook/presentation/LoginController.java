package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.application.UserService;
import com.woowacourse.sunbook.application.dto.UserRequestDto;
import com.woowacourse.sunbook.application.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    private final UserService userService;

    public LoginController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody UserRequestDto userRequestDto) {
        userService.save(userRequestDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> signIn(HttpSession httpSession,
                                                  @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto loginUser = userService.login(userRequestDto);
        httpSession.setAttribute("loginUser", loginUser);

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }
}
