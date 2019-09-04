package techcourse.w3.woostagram.tag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tags/hash/")
public class HashTagController {

    @GetMapping("/{hashTag}")
    public String show(Model model, @PathVariable String hashTag) {
        model.addAttribute("tagName", hashTag);
        return "article-tag";
    }
}
