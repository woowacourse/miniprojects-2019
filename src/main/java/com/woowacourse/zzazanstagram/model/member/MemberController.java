package com.woowacourse.zzazanstagram.model.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/members/{id}")
    public String one(@PathVariable("id") Long id, Model model) {
        MemberResponse memberResponse = memberService.find(id);
        model.addAttribute("member", memberResponse);
        return "login";
    }
}
