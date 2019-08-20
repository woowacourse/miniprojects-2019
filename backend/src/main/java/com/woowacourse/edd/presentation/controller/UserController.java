package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.UserRequestDto;
import com.woowacourse.edd.application.response.UserResponse;
import com.woowacourse.edd.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(userService.findbyId(id));
    }

    @PostMapping
    public ResponseEntity saveUser(@Valid @RequestBody UserRequestDto userSaveRequestDto) {
        return ResponseEntity.created(URI.create(USER_URL + "/" + userService.save(userSaveRequestDto))).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable Long id) {
        return ResponseEntity.ok(userService.update(userRequestDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
