package techcourse.w3.woostagram.like.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LikesController {

    @GetMapping("/likes")
    public String index() {
        return "likes-page";
    }
}
