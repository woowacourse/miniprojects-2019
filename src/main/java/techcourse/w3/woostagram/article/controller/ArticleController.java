package techcourse.w3.woostagram.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    @GetMapping("/form")
    public String createForm() {
        return "article-form";
    }
}
