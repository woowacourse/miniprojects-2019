package techcourse.w3.woostagram.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import techcourse.w3.woostagram.common.support.LoggedInUser;
import techcourse.w3.woostagram.main.service.MainService;

@Controller
public class IndexController {
    private final MainService mainService;

    public IndexController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("{userName}")
    public String showUserPage(Model model, @LoggedInUser String email, @PathVariable String userName) {
        model.addAttribute("userPageInfo", mainService.findUserPageDto(email, userName));
        return "mypage-re";
    }
}
