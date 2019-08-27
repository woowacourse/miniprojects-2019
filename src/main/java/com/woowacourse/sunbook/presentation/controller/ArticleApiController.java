package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.article.ArticleRequestDto;
import com.woowacourse.sunbook.application.dto.article.ArticleResponseDto;
import com.woowacourse.sunbook.application.service.ArticleService;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.presentation.support.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {
    private final ArticleService articleService;

    @Autowired
    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> show() {
        List<ArticleResponseDto> articles = articleService.findAll();

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> save(ArticleRequestDto articleRequestDto, LoginUser loginUser) {
        ArticleResponseDto articleResponseDto = articleService.save(articleRequestDto.getArticleFeature(), loginUser.getId());

        return new ResponseEntity<>(articleResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> modify(@PathVariable Long articleId,
                                                     @RequestBody ArticleFeature articleFeature,
                                                     LoginUser loginUser) {
        ArticleResponseDto articleResponseDto = articleService.modify(articleId, articleFeature, loginUser.getId());

        return new ResponseEntity<>(articleResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> remove(@PathVariable Long articleId, LoginUser loginUser) {
        articleService.remove(articleId, loginUser.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
