package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.UserBasicInfo;
import com.woowacourse.dsgram.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity signUp(@RequestBody UserBasicInfo userBasicInfo) {
        userService.save(userBasicInfo);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
