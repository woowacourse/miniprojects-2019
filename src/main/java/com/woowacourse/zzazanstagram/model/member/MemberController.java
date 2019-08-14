package com.woowacourse.zzazanstagram.model.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String login() {
        return "login";
    }

    @PostMapping("/members")
    public String saveMember(@Valid MemberRequest memberRequest) {
        memberService.save(memberRequest);
        return "redirect:/login";
    }
}
