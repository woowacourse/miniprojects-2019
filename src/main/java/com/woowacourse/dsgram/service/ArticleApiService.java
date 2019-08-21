package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.ArticleRepository;
import com.woowacourse.dsgram.domain.HashTag;
import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import com.woowacourse.dsgram.service.dto.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.user.LoginUserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Transactional
    public Article update(long articleId, ArticleEditRequest articleEditRequest, LoginUserRequest loginUserRequest) {
        Article article = checkAuthor(articleId, loginUserRequest);
        return article.update(articleEditRequest.getContents());
    }

    @Transactional
    public void delete(long articleId, LoginUserRequest loginUserRequest) {
        Article article = checkAuthor(articleId, loginUserRequest);
        articleRepository.delete(article);
    }

    private Article checkAuthor(long articleId, LoginUserRequest loginUserRequest) {
        Article article = findById(articleId);
        if (article.notEqualAuthorId(loginUserRequest.getId())) {
            throw new InvalidUserException("글 작성자만 수정, 삭제가 가능합니다.");
        }
        return article;
    }
}
