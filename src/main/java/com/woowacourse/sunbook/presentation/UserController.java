package com.woowacourse.sunbook.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @GetMapping("/")
    public String signUpPage() {
        return "index";
    }

    @PostMapping("/signout")
    public RedirectView signOut(HttpSession httpSession) {
        httpSession.invalidate();

        return new RedirectView("/");
    }


}
