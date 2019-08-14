package com.woowacourse.zzazanstagram.model.article.service;

import com.woowacourse.zzazanstagram.model.article.ArticleAssembler;
import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleRequest;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleResponse;
import com.woowacourse.zzazanstagram.model.article.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
    private static final String TAG = "[ArticleService]";

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleResponse> getArticleResponses() {
        List<Article> articles = articleRepository.findAllByOrderByIdDesc();

        return articles.stream()
                .map(ArticleAssembler::toDto)
                .collect(Collectors.toList());
    }

    public void save(ArticleRequest dto) {
        Article article = ArticleAssembler.toEntity(dto);
        articleRepository.save(article);

        log.info("{} create() >> {}", TAG, article);
    }
}
