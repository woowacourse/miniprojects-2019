package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.SignUpUserDto;
import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity signUp(@RequestBody SignUpUserDto signUpUserDto) {
        userService.save(signUpUserDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity update(@PathVariable long userId, @RequestBody UserDto userDto) {
        userService.update(userId, userDto);
        return new ResponseEntity(HttpStatus.OK);
    }

}
