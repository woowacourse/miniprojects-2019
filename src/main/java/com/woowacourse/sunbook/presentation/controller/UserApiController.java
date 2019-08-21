package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.dto.user.UserUpdateRequestDto;
import com.woowacourse.sunbook.application.service.UserService;
import com.woowacourse.sunbook.presentation.support.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class UserApiController {
    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponseDto> getLoginUser(LoginUser loginUser) {
        if (loginUser == null) {
            // 예외 이름 결정 및 생성
            throw new IllegalArgumentException();
        }

        return new ResponseEntity<>(loginUser.getUserResponseDto(), HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponseDto> updateUser(LoginUser loginUser, HttpSession httpSession,
                                                      @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        UserResponseDto updateUser = userService.update(loginUser.getUserResponseDto(), userUpdateRequestDto);
        httpSession.setAttribute("loginUser", updateUser);

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
}
