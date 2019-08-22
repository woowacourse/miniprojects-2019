package techcourse.w3.woostagram.article.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.common.support.LoggedInUser;

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
    public String create(ArticleDto articleDto, @LoggedInUser String email) {
        return "redirect:/articles/" + articleService.save(articleDto, email);
    }

    @GetMapping("/{articleId}")
    public String show(@PathVariable Long articleId, Model model) {
        model.addAttribute("articleId", articleId);
        return "article-detail";
    }

    @DeleteMapping("/{articleId}")
    public String delete(@PathVariable Long articleId, @LoggedInUser String email) {
        articleService.deleteById(articleId, email);
        return "redirect:/";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRuntimeException() {
        return "redirect:/";
    }
}
