package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.ArticleRepositoy;
import com.woowacourse.dsgram.service.exception.JpaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleApiService {

    ArticleRepositoy articleRepositoy;

    public ArticleApiService(ArticleRepositoy articleRepository) {
        this.articleRepositoy = articleRepository;
    }

    public Article create(Article article) {
        try {
            return articleRepositoy.save(article);
        } catch (DataAccessException exception) {
            throw new JpaException("게시글 생성에 실패하였습니다.", exception);
        }
    }

    @Transactional(readOnly = true)
    public Article findById(long articleId) {
        return articleRepositoy.findById(articleId).orElseThrow(() -> new JpaException("해당 게시글을 조회하지 못했습니다."));
    }
}
