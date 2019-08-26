package techcourse.w3.woostagram.mypage.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import techcourse.w3.woostagram.common.support.LoggedInUser;
import techcourse.w3.woostagram.mypage.service.MypageService;

@Controller
public class MypageController {
    private final MypageService mypageService;

    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @GetMapping("/{userName}")
    public String showUserPage(Model model, @LoggedInUser String email, @PathVariable String userName) {
        model.addAttribute("userPageInfo", mypageService.findUserPageDto(email, userName));
        return "mypage";
    }
}
