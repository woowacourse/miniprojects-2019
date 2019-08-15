package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.application.ArticleResponseDto;
import com.woowacourse.sunbook.application.ArticleService;
import com.woowacourse.sunbook.domain.ArticleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/articles")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleResponseDto> show() {
        return articleService.findAll();
    }

    @PostMapping
    public ArticleResponseDto save(@RequestBody ArticleFeature articleFeature) {
        return articleService.save(articleFeature);
    }

    @PutMapping("/{articleId}")
    public ArticleFeature modify(@PathVariable long articleId, @RequestBody ArticleFeature articleFeature) {
        return articleService.modify(articleId, articleFeature);
    }

    @DeleteMapping("/{articleId}")
    public void remove(@PathVariable long articleId) {
        articleService.remove(articleId);
    }
}
