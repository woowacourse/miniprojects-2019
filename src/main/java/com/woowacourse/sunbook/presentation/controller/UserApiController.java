package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.dto.user.UserUpdateRequestDto;
import com.woowacourse.sunbook.application.service.UserService;
import com.woowacourse.sunbook.presentation.support.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> show(LoginUser loginUser) {
        return ResponseEntity.ok().body(loginUser.getUserResponseDto());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> show(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.findUser(userId));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> save(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto signUpUser = userService.save(userRequestDto);

        return ResponseEntity.ok().body(signUpUser);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> update(LoginUser loginUser, HttpSession httpSession,
                                                      @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        UserResponseDto updateUser = userService.update(loginUser.getUserResponseDto(), userUpdateRequestDto);
        httpSession.setAttribute("loginUser", updateUser);

        return ResponseEntity.ok().body(updateUser);
    }
}
