package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.UserProfileImageFileService;
import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.EditUserRequest;
import com.woowacourse.dsgram.service.dto.user.LoginUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import com.woowacourse.dsgram.web.controller.exception.InvalidPatternException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final UserService userService;
    private final UserProfileImageFileService userProfileImageFileService;

    public UserApiController(UserService userService, UserProfileImageFileService userProfileImageFileService) {
        this.userService = userService;
        this.userProfileImageFileService = userProfileImageFileService;
    }

    @PostMapping
    public ResponseEntity signUp(@RequestBody @Valid SignUpUserRequest signUpUserRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            throw new InvalidPatternException(fieldError.getDefaultMessage());
        }
        userService.save(signUpUserRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity update(@PathVariable long userId,
                                 @Valid EditUserRequest editUserRequest,
                                 BindingResult bindingResult,
                                 @UserSession LoginUserRequest loginUserRequest) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            throw new RuntimeException(fieldError.getDefaultMessage());
        }

//        FileInfo fileInfo = fileService.save(editUserRequest.getFile());
//        UserProfileImage userProfileImage = convertFrom(userId, fileInfo);
//        userProfileImageService.update(userProfileImage);
        userProfileImageFileService.save(userId, editUserRequest.getFile());
        log.info("file data is empty? : {}", editUserRequest.getFile().isEmpty());
        userService.update(userId, editUserRequest.toUserDto(), loginUserRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthUserRequest authUserRequest, HttpSession httpSession) {
        httpSession.setAttribute("sessionUser", userService.login(authUserRequest));
        return new ResponseEntity(HttpStatus.OK);
    }
}
