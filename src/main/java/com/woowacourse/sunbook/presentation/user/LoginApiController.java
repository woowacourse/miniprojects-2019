package com.woowacourse.sunbook.presentation.user;

import com.woowacourse.sunbook.application.user.LoginService;
import com.woowacourse.sunbook.application.user.dto.UserRequestDto;
import com.woowacourse.sunbook.application.user.dto.UserResponseDto;
import com.woowacourse.sunbook.application.user.dto.UserUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class LoginApiController {
    private final LoginService loginService;

    @Autowired
    public LoginApiController(final LoginService loginService) {
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

    @GetMapping("/users")
    public ResponseEntity<UserResponseDto> getLoginUser(HttpSession httpSession) {
        UserResponseDto loginUser = (UserResponseDto) httpSession.getAttribute("loginUser");

        if (loginUser == null) {
            // 예외 이름 결정 및 생성
            throw new IllegalArgumentException();
        }

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponseDto> updateUser(HttpSession httpSession,
                                                      @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        UserResponseDto loginUser = (UserResponseDto) httpSession.getAttribute("loginUser");
        UserResponseDto updateUser = loginService.update(loginUser, userUpdateRequestDto);
        httpSession.setAttribute("loginUser", updateUser);

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
}
