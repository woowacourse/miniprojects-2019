package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.domain.Article;
import com.woowacourse.sunbook.domain.ArticleFeature;
import com.woowacourse.sunbook.domain.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {

    private static final long ARTICLE_ID = 1L;
    private static final String CONTENTS = "SunBook Contents";
    private static final String IMAGE_URL = "https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8";
    private static final String VIDEO_URL = "https://youtu.be/mw5VIEIvuMI";
    private static final String EMPTY = "";

    private static final String UPDATE_CONTENTS = "Update Contents";
    private static final String UPDATE_IMAGE_URL = "http://mblogthumb2.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800";
    private static final String UPDATE_VIDEO_URL = "https://youtu.be/4HG_CJzyX6A";

    private final Article article = new Article(CONTENTS, IMAGE_URL, VIDEO_URL);

    private final ArticleFeature articleFeature = new ArticleFeature(CONTENTS, IMAGE_URL, VIDEO_URL);
    private final ArticleFeature updatedArticleFeature = new ArticleFeature(UPDATE_CONTENTS, UPDATE_IMAGE_URL, UPDATE_VIDEO_URL);

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    void 게시글_정상_생성() {
        given(articleRepository.save(article)).willReturn(article);

        assertEquals(articleFeature, articleService.save(articleFeature));
    }

    @Test
    void 게시글_정상_수정() {
        given(articleRepository.findById(ARTICLE_ID)).willReturn(Optional.of(article));

        assertEquals(updatedArticleFeature, articleService.modify(ARTICLE_ID, updatedArticleFeature));
    }

    @Test
    void 게시글_정상_삭제() {
        articleService.remove(ARTICLE_ID);
        verify(articleRepository).deleteById(ARTICLE_ID);
    }
}
