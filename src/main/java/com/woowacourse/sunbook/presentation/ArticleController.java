package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.application.ArticleService;
import com.woowacourse.sunbook.application.dto.article.ArticleResponseDto;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ArticleResponseDto>> show() {
        List<ArticleResponseDto> articles = articleService.findAll();

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> save(@RequestBody ArticleFeature articleFeature) {
        ArticleResponseDto articleResponseDto = articleService.save(articleFeature);

        return new ResponseEntity<>(articleResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> modify(@PathVariable long articleId, @RequestBody ArticleFeature articleFeature) {
        ArticleResponseDto articleResponseDto = articleService.modify(articleId, articleFeature);

        return new ResponseEntity<>(articleResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> remove(@PathVariable long articleId) {
        articleService.remove(articleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
