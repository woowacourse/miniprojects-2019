package techcourse.w3.woostagram.article.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.service.ArticleService;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Value("${file.upload.directory}")
    private String uploadPath;
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/form")
    public String createForm() {
        return "article-form";
    }

    @PostMapping
    public String create(ArticleDto articleDto) {
        articleService.save(articleDto);
        return "redirect:/articles/form";
    }
}
