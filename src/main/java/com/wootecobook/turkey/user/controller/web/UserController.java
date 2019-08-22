package com.wootecobook.turkey.user.controller.web;

import com.wootecobook.turkey.user.domain.User;
import com.wootecobook.turkey.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/form")
    public ModelAndView createForm() {
        return new ModelAndView("signup");
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {
        User user = userService.findById(id);

        return new ModelAndView("mypage", "user", user);
    }
}
