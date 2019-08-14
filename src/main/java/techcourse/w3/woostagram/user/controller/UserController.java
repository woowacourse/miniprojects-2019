package techcourse.w3.woostagram.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.user.dto.UserContentsDto;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
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
    public String loginForm() {
        return "login";
    }

    @PostMapping("login")
    public String login(UserDto userDto, HttpSession httpSession) {
        String email = userService.authUser(userDto);
        httpSession.setAttribute("email", email);
        return "index";
    }

    @GetMapping("signup/form")
    public String createForm() {
        return "signup";
    }

    @PostMapping("signup")
    public String create(UserDto userDto) {
        userService.create(userDto);
        return "redirect:/users/login/form";
    }

    @GetMapping("mypage")
    public String show(Model model, HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        UserInfoDto userInfoDto = userService.findByEmail(email);
        model.addAttribute("userInfo", userInfoDto);
        return "mypage";
    }

    @GetMapping("mypage-edit/form")
    public String updateForm(Model model, HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        UserInfoDto userInfoDto = userService.findByEmail(email);
        model.addAttribute("userInfo", userInfoDto);
        return "mypage-edit";
    }

    @PutMapping
    public String update(@Valid UserContentsDto userContentsDto, HttpSession httpSession) {

        String email = (String) httpSession.getAttribute("email");
        userService.update(userContentsDto, email);
        return "redirect:/users/mypage";
    }

    @DeleteMapping
    public String delete(HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        userService.delete(email);
        return "redirect:/users/login/form";
    }
}
