package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.application.dto.article.ArticleResponseDto;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.article.ArticleRepository;
import com.woowacourse.sunbook.presentation.excpetion.NotFoundArticleException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleResponseDto save(ArticleFeature articleFeature) {
        Article savedArticle = articleRepository.save(new Article(articleFeature.getContents(), articleFeature.getImageUrl(), articleFeature.getVideoUrl()));
        return new ArticleResponseDto(savedArticle.getId(), savedArticle.getArticleFeature(), savedArticle.getUpdatedTime());
    }

    @Transactional
    public ArticleResponseDto modify(long articleId, ArticleFeature articleFeature) {
        Article article = articleRepository.findById(articleId).orElseThrow(NotFoundArticleException::new);
        article.update(articleFeature);
        return new ArticleResponseDto(articleId, articleFeature, article.getUpdatedTime());
    }

    public List<ArticleResponseDto> findAll() {
        return Collections.unmodifiableList(
                articleRepository.findAll().stream()
                        .map(article -> new ArticleResponseDto(article.getId(), article.getArticleFeature(),
                                article.getUpdatedTime()))
                        .collect(Collectors.toList())
        );
    }

    public void remove(long articleId) {
        articleRepository.deleteById(articleId);
    }
}
