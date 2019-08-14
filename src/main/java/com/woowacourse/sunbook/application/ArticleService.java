package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.domain.Article;
import com.woowacourse.sunbook.domain.ArticleFeature;
import com.woowacourse.sunbook.domain.ArticleRepository;
import com.woowacourse.sunbook.presentation.NotFoundArticleException;
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
    public ArticleFeature modify(long articleId, ArticleFeature articleFeature) {
        Article article = articleRepository.findById(articleId).orElseThrow(NotFoundArticleException::new);
        article.update(articleFeature);
        return articleFeature;
    }

    public List<ArticleFeature> findAll() {
        return Collections.unmodifiableList(
                articleRepository.findAll().stream()
                        .map(Article::getArticleFeature)
                        .collect(Collectors.toList())
        );
    }

    public void remove(long articleId) {
        articleRepository.deleteById(articleId);
    }
}
