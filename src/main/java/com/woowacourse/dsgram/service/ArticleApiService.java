package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.ArticleRepository;
import com.woowacourse.dsgram.domain.HashTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
public class ArticleApiService {

    private final ArticleRepository articleRepository;
    private final HashTagService hashTagService;

    public ArticleApiService(ArticleRepository articleRepository, HashTagService hashTagService) {
        this.articleRepository = articleRepository;
        this.hashTagService = hashTagService;
    }

    @Transactional
    public Article create(Article article) {
        Article savedArticle = articleRepository.save(article);

        hashTagService.saveHashTags(
                article.getKeyword().stream()
                        .map(keyword -> new HashTag(keyword, savedArticle))
                        .collect(Collectors.toList()));

        return savedArticle;
    }

    @Transactional(readOnly = true)
    public Article findById(long articleId) {
        return articleRepository
                .findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(articleId + "번 게시글을 조회하지 못했습니다."));
    }
}
