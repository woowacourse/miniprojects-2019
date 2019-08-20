package techcourse.w3.woostagram.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.dto.UserUpdateDto;
import techcourse.w3.woostagram.user.service.UserService;
import techcourse.w3.woostagram.user.support.LoggedInUser;

import javax.servlet.http.HttpSession;

import static techcourse.w3.woostagram.user.support.UserEmailArgumentResolver.LOGGED_IN_USER_SESSION_KEY;

@Controller
@RequestMapping("users")
public class UserController {
    private final UserService userService;

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
        httpSession.setAttribute(LOGGED_IN_USER_SESSION_KEY, email);
        return "redirect:/";
    }

    @GetMapping("signup/form")
    public String createForm() {
        return "signup";
    }

    @PostMapping("signup")
    public String create(UserDto userDto) {
        userService.save(userDto);
        return "redirect:/users/login/form";
    }

    @GetMapping("mypage")
    public String show(Model model, @LoggedInUser String email) {
        UserInfoDto userInfoDto = userService.findByEmail(email);
        model.addAttribute("userInfo", userInfoDto);
        return "mypage";
    }

    @GetMapping("mypage-edit/form")
    public String updateForm(Model model, @LoggedInUser String email) {
        UserInfoDto userInfoDto = userService.findByEmail(email);
        model.addAttribute("userInfo", userInfoDto);
        return "mypage-edit";
    }

    @PutMapping
    public String update(UserUpdateDto userUpdateDto, @LoggedInUser String email) {
        userService.update(userUpdateDto, email);
        return "redirect:/users/mypage";
    }

    @DeleteMapping
    public String delete(@LoggedInUser String email) {
        userService.deleteByEmail(email);
        return "redirect:/users/login/form";
    }
}
