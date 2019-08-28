package com.woowacourse.sunbook.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/")
    public String signUpPage() {
        return "index";
    }

    @GetMapping("/signout")
    public RedirectView signOut(HttpSession httpSession) {
        httpSession.invalidate();

        return new RedirectView("/");
    }

    @GetMapping("/newsfeed")
    public String sunbookPage() {
        return "newsfeed";
    }

    @GetMapping("/users/{userId}")
    public String users() {
        return "users";
    }
}
