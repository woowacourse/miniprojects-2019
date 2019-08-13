package techcourse.w3.woostagram.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.user.dto.UserContentsDto;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login/form")
    public String show() {
        return "login";
    }

    @GetMapping("signup/form")
    public String createFrom() {
        return "signup";
    }

    @PostMapping("signup")
    public String create(@Valid UserDto userDto) {
        userService.create(userDto);
        return "redirect:/login/form";
    }

    @PostMapping("login")
    public String login(UserDto userDto, HttpSession httpSession) {
        String email = userService.authUser(userDto);
        httpSession.setAttribute("email", email);
        return "redirect:/index";
    }

    @PutMapping
    public String update(UserContentsDto userContentsDto, HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        userService.update(userContentsDto, email);
        return "mypage";
    }

    @DeleteMapping
    public String delete(HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        userService.delete(email);
        //TODO : 로그인 안한 사람이 보는 페이지 만들기
        return "index";
    }
}
