package techcourse.w3.woostagram.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.common.support.LoggedInUser;
import techcourse.w3.woostagram.common.support.UserRateLimiter;
import techcourse.w3.woostagram.user.dto.UserCreateDto;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.dto.UserUpdateDto;
import techcourse.w3.woostagram.user.exception.UserCreateException;
import techcourse.w3.woostagram.user.exception.UserUpdateException;
import techcourse.w3.woostagram.user.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static techcourse.w3.woostagram.common.support.UserEmailArgumentResolver.LOGGED_IN_USER_SESSION_KEY;

@Controller
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final UserRateLimiter userRateLimiter;

    public UserController(final UserService userService, final UserRateLimiter userRateLimiter) {
        this.userService = userService;
        this.userRateLimiter = userRateLimiter;
    }

    @GetMapping("login/form")
    public String loginForm() {
        return "login";
    }

    @PostMapping("login")
    public String login(UserDto userDto, HttpSession httpSession) {
        httpSession.setAttribute(LOGGED_IN_USER_SESSION_KEY, userService.authUser(userDto));
        userRateLimiter.put(userDto.getEmail());
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session, @LoggedInUser String email) {
        userRateLimiter.remove(email);
        session.removeAttribute(LOGGED_IN_USER_SESSION_KEY);
        return "redirect:/users/login/form";
    }

    @GetMapping("signup/form")
    public String createForm() {
        return "signup";
    }

    @PostMapping("signup")
    public String create(@Valid UserCreateDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new UserCreateException(result.getFieldError().getDefaultMessage());
        }
        userService.save(userDto);
        return "redirect:/users/login/form";
    }

    @GetMapping("mypage-edit/form")
    public String updateForm(Model model, @LoggedInUser String email) {
        UserInfoDto userInfoDto = userService.findByEmail(email);
        model.addAttribute("userInfo", userInfoDto);
        return "mypage-edit";
    }

    @PutMapping
    public String update(@Valid UserUpdateDto userUpdateDto, BindingResult result, @LoggedInUser String email, HttpSession httpSession) {
        if (result.hasErrors()) {
            throw new UserUpdateException(result.getFieldError().getDefaultMessage());
        }
        userService.update(userUpdateDto, email);
        UserInfoDto userInfoDto = userService.findByEmail(email);
        httpSession.setAttribute(LOGGED_IN_USER_SESSION_KEY, userService.findByEmail(email));
        return "redirect:/" + userInfoDto.getUserContentsDto().getUserName();
    }

    @DeleteMapping
    public String delete(@LoggedInUser String email, HttpSession httpSession) {
        httpSession.removeAttribute(LOGGED_IN_USER_SESSION_KEY);
        userService.deleteByEmail(email);
        return "redirect:/users/login/form";
    }
}
