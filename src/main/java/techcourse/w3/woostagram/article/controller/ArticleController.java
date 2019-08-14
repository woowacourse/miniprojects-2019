package techcourse.w3.woostagram.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.service.ArticleService;

@Controller
@RequestMapping("/articles")
public class ArticleController {
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
        return "redirect:/articles/" + articleService.save(articleDto);
    }

    @GetMapping("/{articleId}")
    public String show(@PathVariable Long articleId, Model model) {
        model.addAttribute("articleId", articleId);
        return "article-detail";
    }

    @DeleteMapping("/{articleId}")
    public String delete(@PathVariable Long articleId) {
        articleService.remove(articleId);
        return "redirect:/";
    }
}
