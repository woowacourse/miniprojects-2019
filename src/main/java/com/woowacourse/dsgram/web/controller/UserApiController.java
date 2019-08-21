package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.UserProfileImage;
import com.woowacourse.dsgram.service.FileService;
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
    private final FileService fileService;
    private final UserProfileImageFileService userProfileImageFileService;

    public UserApiController(UserService userService, UserProfileImageFileService userProfileImageFileService, FileService fileService) {
        this.userService = userService;
        this.userProfileImageFileService = userProfileImageFileService;
        this.fileService = fileService;
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

        editUserRequest.getFile().ifPresent((uploadedFile) ->
                userProfileImageFileService.saveOrUpdate(userId, uploadedFile));

        userService.update(userId, editUserRequest.toUserDto(), loginUserRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthUserRequest authUserRequest, HttpSession httpSession) {
        httpSession.setAttribute("sessionUser", userService.login(authUserRequest));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{userId}/image")
    public ResponseEntity<byte[]> showArticleFile(@PathVariable long userId) {
        UserProfileImage userProfileImage = userProfileImageFileService.findById(userId);
        byte[] base64 = fileService.readFile(userProfileImage);

        return new ResponseEntity<>(base64, HttpStatus.OK);
    }
}
