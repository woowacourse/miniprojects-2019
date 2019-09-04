package techcourse.w3.woostagram.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.exception.RequestTooFastException;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.common.support.LoggedInUser;
import techcourse.w3.woostagram.common.support.UserRateLimiter;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private ArticleService articleService;
    private final UserRateLimiter userRateLimiter;

    public ArticleController(final ArticleService articleService, final UserRateLimiter userRateLimiter) {
        this.articleService = articleService;
        this.userRateLimiter = userRateLimiter;
    }

    @GetMapping("/form")
    public String createForm() {
        return "article-form";
    }

    @PostMapping
    public String create(ArticleDto articleDto, @LoggedInUser String email) {
        return "redirect:/articles/" + articleService.save(articleDto, email);
    }

    @GetMapping("/{articleId}")
    public String show(@PathVariable Long articleId, Model model) {
        model.addAttribute("article", articleService.findArticleById(articleId));
        return "article-detail";
    }

    @DeleteMapping("/{articleId}")
    public String delete(@PathVariable Long articleId, @LoggedInUser String email) {
        articleService.deleteById(articleId, email);
        return "redirect:/";
    }
}
