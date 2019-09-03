package com.woowacourse.sunbook.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/")
    public String signUpPage() {
        return "index";
    }

    @DeleteMapping("/signout")
    @ResponseBody
    public ResponseEntity signOut(HttpSession httpSession) {
        httpSession.invalidate();

        return ResponseEntity.ok().build();
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
