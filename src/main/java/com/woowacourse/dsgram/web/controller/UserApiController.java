package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.user.AuthUserDto;
import com.woowacourse.dsgram.service.dto.user.LoginUserDto;
import com.woowacourse.dsgram.service.dto.user.SignUpUserDto;
import com.woowacourse.dsgram.service.dto.user.UserDto;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity signUp(@RequestBody @Valid SignUpUserDto signUpUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            throw new RuntimeException(fieldError.getDefaultMessage());
        }
        userService.save(signUpUserDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity update(@PathVariable long userId,
                                 @RequestBody @Valid UserDto updatedUserDto,
                                 BindingResult bindingResult,
                                 @UserSession LoginUserDto loginUserDto) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            throw new RuntimeException(fieldError.getDefaultMessage());
        }
        userService.update(userId, updatedUserDto, loginUserDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthUserDto authUserDto, HttpSession httpSession) {
        httpSession.setAttribute("sessionUser", userService.login(authUserDto));
        return new ResponseEntity(HttpStatus.OK);
    }
}
