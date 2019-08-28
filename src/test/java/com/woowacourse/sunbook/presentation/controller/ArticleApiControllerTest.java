package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.domain.Content;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.fileurl.FileUrl;
import com.woowacourse.sunbook.presentation.template.TestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.hasItem;

class ArticleApiControllerTest extends TestTemplate {
    private static final String CONTENTS = "SunBook Contents";
    private static final String IMAGE_URL = "https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8";
    private static final String VIDEO_URL = "https://youtu.be/mw5VIEIvuMI";

    private static final String UPDATE_CONTENTS = "Update Contents";
    private static final String UPDATE_IMAGE_URL = "http://mblogthumb2.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800";
    private static final String UPDATE_VIDEO_URL = "https://youtu.be/4HG_CJzyX6A";

    private static final Content commentFeature = new Content(CONTENTS);
    private static final FileUrl imageUrl = new FileUrl(IMAGE_URL);
    private static final FileUrl videoUrl = new FileUrl(VIDEO_URL);

    private static final Content updatedContent = new Content(UPDATE_CONTENTS);
    private static final FileUrl updatedImageUrl = new FileUrl(UPDATE_IMAGE_URL);
    private static final FileUrl updatedVideoUrl = new FileUrl(UPDATE_VIDEO_URL);

    @Test
    void 게시글_전체_조회() {
        respondApi(loginAndRequest(HttpMethod.GET, "/api/articles", Void.class, HttpStatus.OK, loginSessionId(userRequestDto)))
                .jsonPath("$..contents.contents").value(hasItem(CONTENTS))
                .jsonPath("$..imageUrl.fileUrl").value(hasItem(IMAGE_URL))
                .jsonPath("$..videoUrl.fileUrl").value(hasItem(VIDEO_URL))
                ;
    }

    @Test
    void 게시글_정상_작성() {
        ArticleFeature articleFeature = new ArticleFeature(commentFeature, imageUrl, videoUrl);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/articles", articleFeature, HttpStatus.OK, loginSessionId(userRequestDto)))
                .jsonPath("$..contents.contents").isEqualTo(CONTENTS)
                .jsonPath("$..imageUrl.fileUrl").isEqualTo(IMAGE_URL)
                .jsonPath("$..videoUrl.fileUrl").isEqualTo(VIDEO_URL)
                ;
    }

    @Test
    void 게시글_업데이트() {
        ArticleFeature updatedArticleFeature = new ArticleFeature(updatedContent, updatedImageUrl, updatedVideoUrl);
        respondApi(loginAndRequest(HttpMethod.PUT, "/api/articles/2", updatedArticleFeature, HttpStatus.OK, loginSessionId(userRequestDto)))
                .jsonPath("$..contents.contents").isEqualTo(UPDATE_CONTENTS)
                .jsonPath("$..imageUrl.fileUrl").isEqualTo(UPDATE_IMAGE_URL)
                .jsonPath("$..videoUrl.fileUrl").isEqualTo(UPDATE_VIDEO_URL)
                ;
    }

    @Test
    void 게시글_정상_삭제() {
        loginAndRequest(HttpMethod.DELETE, "/api/articles/3", Void.class, HttpStatus.OK, loginSessionId(userRequestDto));
    }
}
