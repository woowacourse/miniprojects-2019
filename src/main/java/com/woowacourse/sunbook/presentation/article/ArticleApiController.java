package com.woowacourse.sunbook.presentation.article;

import com.woowacourse.sunbook.application.article.ArticleService;
import com.woowacourse.sunbook.application.article.dto.ArticleResponseDto;
import com.woowacourse.sunbook.application.user.dto.UserResponseDto;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public ResponseEntity<ArticleResponseDto> save(@RequestBody ArticleFeature articleFeature,
                                                   HttpSession httpSession) {
//        UserResponseDto loginUser = (UserResponseDto) httpSession.getAttribute("loginUser");
        // 테스트용
        UserResponseDto loginUser = new UserResponseDto(1L);
        ArticleResponseDto articleResponseDto = articleService.save(articleFeature, loginUser);

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
