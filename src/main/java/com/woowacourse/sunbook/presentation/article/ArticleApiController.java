package com.woowacourse.sunbook.presentation.article;

import com.woowacourse.sunbook.application.article.ArticleService;
import com.woowacourse.sunbook.application.article.dto.ArticleResponseDto;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.seongmo.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<List<ArticleResponseDto>> show(@PageableDefault(size = 2,
            sort = "updatedTime", direction = Sort.Direction.DESC) Pageable pageable, UserSession userSession) {
        List<ArticleResponseDto> articles = articleService.findPageByAuthor(pageable, userSession.getId());

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> save(@RequestBody ArticleFeature articleFeature, UserSession userSession) {
        ArticleResponseDto articleResponseDto = articleService.save(articleFeature, userSession.getId());

        return new ResponseEntity<>(articleResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> modify(@PathVariable long articleId,
                                                     @RequestBody ArticleFeature articleFeature,
                                                     UserSession userSession) {
        ArticleResponseDto articleResponseDto = articleService.modify(articleId, articleFeature, userSession.getId());

        return new ResponseEntity<>(articleResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> remove(@PathVariable long articleId, UserSession userSession) {
        articleService.remove(articleId, userSession.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
