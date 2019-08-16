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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {

    private static final long ARTICLE_ID = 1L;
    private static final String CONTENTS = "SunBook Contents";
    private static final String IMAGE_URL = "https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8";
    private static final String VIDEO_URL = "https://youtu.be/mw5VIEIvuMI";

    private final Article article = new Article(CONTENTS, IMAGE_URL, VIDEO_URL);

    private final ArticleFeature articleFeature = mock(ArticleFeature.class);

    private final ArticleFeature updatedArticleFeature = mock(ArticleFeature.class);

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    void 게시글_정상_생성() {
        given(articleRepository.save(any(Article.class))).willReturn(article);
        articleService.save(articleFeature);

        verify(articleRepository).save(article);
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
