package com.woowacourse.zzazanstagram.model.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(MemberLoginRequest memberLoginRequest, HttpSession httpSession) {
        MemberResponse memberResponse = memberService.find(memberLoginRequest);
        httpSession.setAttribute("user", new UserSession(memberResponse.getEmail()));
        return "redirect:/";
    }

    @PostMapping("/members")
    public String saveMember(@Valid MemberSignUpRequest memberSignupRequest) {
        memberService.save(memberSignupRequest);
        return "redirect:/login";
    }
}
