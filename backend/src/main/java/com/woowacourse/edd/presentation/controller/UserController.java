package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.UserSaveRequestDto;
import com.woowacourse.edd.application.dto.UserUpdateRequestDto;
import com.woowacourse.edd.application.response.SessionUser;
import com.woowacourse.edd.application.response.UserResponse;
import com.woowacourse.edd.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

import static com.woowacourse.edd.presentation.controller.UserController.USER_URL;

@RestController
@RequestMapping(USER_URL)
public class UserController {

    public static final String USER_URL = "/v1/users";

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> retrieveUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity saveUser(@Valid @RequestBody UserSaveRequestDto userSaveRequestDto) {
        return ResponseEntity.created(URI.create(USER_URL + "/" + userService.save(userSaveRequestDto))).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, HttpSession session,
                                                   @Valid @RequestBody UserUpdateRequestDto userSaveRequestDto) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        return ResponseEntity.ok(userService.update(id, sessionUser.getId(), userSaveRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        userService.delete(id, sessionUser.getId());
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
}
