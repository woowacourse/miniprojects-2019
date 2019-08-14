package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignUp() {
        return "signup";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/user/edit/{userId}")
    public String showUserEdit(@PathVariable long userId, Model model) {
        model.addAttribute("user", userService.findUserInfoById(userId));
        return "account-edit";
    }

}
