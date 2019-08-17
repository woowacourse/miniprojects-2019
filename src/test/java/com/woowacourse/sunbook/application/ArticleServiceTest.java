package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.article.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {

    private static final long ARTICLE_ID = 1L;

    @Mock
    private ArticleFeature articleFeature;

    @Mock
    private ArticleFeature updatedArticleFeature;

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private Article article;

    @Test
    void 게시글_정상_생성() {
        given(articleRepository.save(any(Article.class))).willReturn(article);

        articleService.save(articleFeature);

        verify(articleRepository).save(any(Article.class));
    }

    @Test
    void 게시글_정상_수정() {
        given(articleRepository.findById(ARTICLE_ID)).willReturn(Optional.of(article));
        articleService.modify(ARTICLE_ID, updatedArticleFeature);

        verify(articleRepository).findById(ARTICLE_ID);
    }

    @Test
    void 게시글_정상_삭제() {
        articleService.remove(ARTICLE_ID);
        verify(articleRepository).deleteById(ARTICLE_ID);
    }
}
