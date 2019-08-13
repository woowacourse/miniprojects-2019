package techcourse.w3.woostagram.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "";
    }

    @GetMapping("/signup")
    public String showSignPage() {
        return "singup";
    }
}
