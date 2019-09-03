package techcourse.w3.woostagram.explore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ExploreController {

    @GetMapping("/recommended")
    public String show() {
        return "recommended";
    }
}
