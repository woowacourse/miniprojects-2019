package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.ArticleRepository;
import com.woowacourse.dsgram.service.dto.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article create(Article article) {
        return articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public Article findById(long articleId) {
        return articleRepository
                .findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(articleId + "번 게시글을 조회하지 못했습니다."));
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Transactional
    public Article update(long articleId, ArticleEditRequest articleEditRequest, LoggedInUser loggedInUser) {
        Article article = findById(articleId);
        return article.update(articleEditRequest.getContents(), loggedInUser.getId());
    }

    @Transactional
    public void delete(long articleId, LoggedInUser loggedInUser) {
        Article article = findById(articleId);
        article.checkAccessibleAuthor(loggedInUser.getId());
        articleRepository.delete(article);
    }
}
