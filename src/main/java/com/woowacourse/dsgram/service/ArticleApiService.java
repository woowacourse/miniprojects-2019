package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.ArticleRepositoy;
import com.woowacourse.dsgram.service.exception.JpaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleApiService {

    @Autowired
    ArticleRepositoy articleRepositoy;

    public void create(Article article) {
        try {
            articleRepositoy.save(article);
        } catch (Exception exception) {
            throw new JpaException("게시글 생성에 실패하였습니다.", exception);
        }
    }

}
