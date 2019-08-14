package techcourse.w3.woostagram.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.article.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {
    private final ArticleService articleService;

    public ArticleRestController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDto> read(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.get(articleId));
    }

    @PutMapping
    public ResponseEntity<ArticleDto> update(@RequestBody ArticleDto articleDto) {
        articleService.update(articleDto);
        return ResponseEntity.ok(null);
    }
}
