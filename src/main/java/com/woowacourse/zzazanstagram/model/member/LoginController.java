package com.woowacourse.zzazanstagram.model.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

import static com.woowacourse.zzazanstagram.util.SessionKeys.MEMBER;

@Controller
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(MemberLoginRequest memberLoginRequest, HttpSession httpSession) {
        MemberResponse memberResponse = loginService.find(memberLoginRequest);
        httpSession.setAttribute(MEMBER, new MemberSession(memberResponse.getEmail(), memberResponse.getNickName(), memberResponse.getProfileImage()));
        return "redirect:/";
    }
}
