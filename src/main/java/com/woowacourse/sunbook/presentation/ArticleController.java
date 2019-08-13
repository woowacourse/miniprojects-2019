package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.domain.Article;
import com.woowacourse.sunbook.domain.ArticleFeature;
import com.woowacourse.sunbook.domain.ArticleRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/articles")
@RestController
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping
    public List<ArticleFeature> save(@RequestBody ArticleFeature articleFeature) {
        articleRepository.save(new Article(articleFeature.getContents(), articleFeature.getImageUrl(), articleFeature.getVideoUrl()));
        return articleRepository.findAll().stream()
                .map(Article::getArticleFeature)
                .collect(Collectors.toList())
                ;
    }
}
