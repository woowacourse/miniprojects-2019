package techcourse.fakebook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import techcourse.fakebook.controller.utils.OptionalSessionUser;
import techcourse.fakebook.service.FriendshipService;
import techcourse.fakebook.service.LoginService;
import techcourse.fakebook.service.TotalService;
import techcourse.fakebook.service.UserService;
import techcourse.fakebook.service.dto.LoginRequest;
import techcourse.fakebook.service.dto.UserOutline;
import techcourse.fakebook.service.dto.UserResponse;
import techcourse.fakebook.service.dto.UserSignupRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserWebController {
    private static final Logger log = LoggerFactory.getLogger(UserWebController.class);

    private final UserService userService;
    private final LoginService loginService;
    private final TotalService totalService;
    private final FriendshipService friendshipService;

    public UserWebController(UserService userService, LoginService loginService, TotalService totalService, FriendshipService friendshipService) {
        this.userService = userService;
        this.loginService = loginService;
        this.totalService = totalService;
        this.friendshipService = friendshipService;
    }

    @PostMapping
    public String create(@Valid UserSignupRequest userSignupRequest, BindingResult bindingResult, HttpSession session) {
        log.debug("begin");

        if (bindingResult.hasErrors()) {
            return "index";
        }

        userService.save(userSignupRequest);

        LoginRequest loginRequest = new LoginRequest(userSignupRequest.getEmail(), userSignupRequest.getPassword());
        UserOutline userOutline = loginService.login(loginRequest);
        session.setAttribute(LoginController.SESSION_USER_KEY, userOutline);

        return "redirect:/newsfeed";
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable Long userId, @OptionalSessionUser Optional<UserOutline> userOutline, Model model) {
        log.debug("begin");

        UserResponse userResponse = userService.findById(userId);
        model.addAttribute("user", userResponse);
        model.addAttribute("articles", totalService.findArticlesByUser(userId));

        userOutline.ifPresent(loggedInUserOutline -> {
            log.debug("logged-in user..!!");
            model.addAttribute("isFriend", friendshipService.hasFriendship(loggedInUserOutline.getId(), userId));
        });

        return "profile";
    }

    @DeleteMapping("/{userId}")
    public String delete(@PathVariable Long userId) {
        log.debug("begin");

        userService.deleteById(userId);

        return "redirect:/";
    }
}
