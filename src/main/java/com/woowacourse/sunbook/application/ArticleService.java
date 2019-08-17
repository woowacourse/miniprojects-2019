package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.application.dto.article.ArticleResponseDto;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.article.ArticleRepository;
import com.woowacourse.sunbook.presentation.excpetion.NotFoundArticleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
    public ArticleResponseDto save(ArticleFeature articleFeature) {
        Article savedArticle = articleRepository.save(new Article(articleFeature));

        return ArticleResponseDto.builder()
                .id(savedArticle.getId())
                .articleFeature(savedArticle.getArticleFeature())
                .updatedTime(savedArticle.getUpdatedTime())
                .build();
    }

    @Transactional
    public ArticleResponseDto modify(long articleId, ArticleFeature articleFeature) {
        Article article = articleRepository.findById(articleId).orElseThrow(NotFoundArticleException::new);
        article.update(articleFeature);

        return ArticleResponseDto.builder()
                .id(article.getId())
                .articleFeature(article.getArticleFeature())
                .updatedTime(article.getUpdatedTime())
                .build();
    }

    public List<ArticleResponseDto> findAll() {
        return Collections.unmodifiableList(
                articleRepository.findAll().stream()
                        .map(article -> ArticleResponseDto.builder()
                                .id(article.getId())
                                .articleFeature(article.getArticleFeature())
                                .updatedTime(article.getUpdatedTime())
                                .build()
                        )
                        .collect(Collectors.toList())
        );
    }

    public void remove(long articleId) {
        articleRepository.deleteById(articleId);
    }
}
