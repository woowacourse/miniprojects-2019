package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.domain.Content;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.fileurl.FileUrl;
import com.woowacourse.sunbook.presentation.template.TestTemplate;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArticleApiControllerTest extends TestTemplate {
    private static final Long USER_ID = 1L;
    private static final Long FRIEND_PAGE_USER_ID = 2L;
    private static final Long STRANGER_PAGE_USER_ID = 3L;
    private static final String NEWSFEED_PAGE = "newsfeed";
    private static final String USER_PAGE = "users";

    private static final String RANGE_ALL = "0";
    private static final String RANGE_FRIEND = "1";
    private static final String RANGE_NONE = "2";

    private static final String ALL_CONTENTS = "SunBook Contents";
    private static final String IMAGE_URL = "https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8";
    private static final String VIDEO_URL = "https://youtu.be/mw5VIEIvuMI";

    private static final String UPDATE_CONTENTS = "Update Contents";
    private static final String UPDATE_IMAGE_URL = "http://mblogthumb2.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800";
    private static final String UPDATE_VIDEO_URL = "https://youtu.be/4HG_CJzyX6A";

    private static final String FRIEND_CONTENTS = "show friend contents";
    private static final String NONE_CONTENTS = "show none contents";

    private static final Content updatedContent = new Content(UPDATE_CONTENTS);
    private static final FileUrl updatedImageUrl = new FileUrl(UPDATE_IMAGE_URL);
    private static final FileUrl updatedVideoUrl = new FileUrl(UPDATE_VIDEO_URL);

    @Test
    void 뉴스피드_게시글_조회() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles")
                        .queryParam("target", NEWSFEED_PAGE)
                        .queryParam("pageUserId", USER_ID)
                        .build())
                .cookie("JSESSIONID", loginSessionId(userRequestDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(res.getResponseBody());
                    assertTrue(body.contains(ALL_CONTENTS));
                    assertFalse(body.contains(NONE_CONTENTS));
                })
                ;
    }

    @Test
    void 자신의_마이페이지_게시글_조회() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles")
                        .queryParam("target", USER_PAGE)
                        .queryParam("pageUserId", USER_ID)
                        .build())
                .cookie("JSESSIONID", loginSessionId(userRequestDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(res.getResponseBody());
                    assertTrue(body.contains(ALL_CONTENTS));
                    assertFalse(body.contains(NONE_CONTENTS));
                })
        ;
    }

    @Test
    void 친구_마이페이지_게시글_조회() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles")
                        .queryParam("target", USER_PAGE)
                        .queryParam("pageUserId", FRIEND_PAGE_USER_ID)
                        .build())
                .cookie("JSESSIONID", loginSessionId(userRequestDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(res.getResponseBody());
                    assertFalse(body.contains(NONE_CONTENTS));
                })
        ;
    }

    @Test
    void 친구가_아닌_타인의_마이페이지_게시글_조회() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles")
                        .queryParam("target", USER_PAGE)
                        .queryParam("pageUserId", STRANGER_PAGE_USER_ID)
                        .build())
                .cookie("JSESSIONID", loginSessionId(userRequestDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(res.getResponseBody());
                    assertFalse(body.contains(FRIEND_CONTENTS));
                    assertFalse(body.contains(NONE_CONTENTS));
                })
        ;
    }

    @Test
    void 전체_공개_게시글_정상_작성() {
        JSONObject articleJson = new JSONObject();
        articleJson.put("contents", ALL_CONTENTS);
        articleJson.put("imageUrl", IMAGE_URL);
        articleJson.put("videoUrl", VIDEO_URL);
        articleJson.put("openRange", RANGE_ALL);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/articles", articleJson, HttpStatus.OK, loginSessionId(userRequestDto)))
                .jsonPath("$..contents.contents").isEqualTo(ALL_CONTENTS)
                .jsonPath("$..imageUrl.fileUrl").isEqualTo(IMAGE_URL)
                .jsonPath("$..videoUrl.fileUrl").isEqualTo(VIDEO_URL)
        ;
    }

    @Test
    void 친구_공개_게시글_정상_작성() {
        JSONObject articleJson = new JSONObject();
        articleJson.put("contents", FRIEND_CONTENTS);
        articleJson.put("imageUrl", IMAGE_URL);
        articleJson.put("videoUrl", VIDEO_URL);
        articleJson.put("openRange", RANGE_FRIEND);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/articles", articleJson, HttpStatus.OK, loginSessionId(userRequestDto)))
                .jsonPath("$..contents.contents").isEqualTo(FRIEND_CONTENTS)
                .jsonPath("$..imageUrl.fileUrl").isEqualTo(IMAGE_URL)
                .jsonPath("$..videoUrl.fileUrl").isEqualTo(VIDEO_URL)
        ;
    }

    @Test
    void 비공개_게시글_정상_작성() {
        JSONObject articleJson = new JSONObject();
        articleJson.put("contents", NONE_CONTENTS);
        articleJson.put("imageUrl", IMAGE_URL);
        articleJson.put("videoUrl", VIDEO_URL);
        articleJson.put("openRange", RANGE_NONE);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/articles", articleJson, HttpStatus.OK, loginSessionId(userRequestDto)))
                .jsonPath("$..contents.contents").isEqualTo(NONE_CONTENTS)
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
