package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.domain.ArticleFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.hasItem;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerTest {

    private static final String CONTENTS = "SunBook Contents";
    private static final String IMAGE_URL = "https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8";
    private static final String VIDEO_URL = "https://youtu.be/mw5VIEIvuMI";
    private static final String EMPTY = "";

    private static final String UPDATE_CONTENTS = "Update Contents";
    private static final String UPDATE_IMAGE_URL = "http://mblogthumb2.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800";
    private static final String UPDATE_VIDEO_URL = "https://youtu.be/4HG_CJzyX6A";

    @Autowired
    WebTestClient webTestClient;

    @Test
    void 게시글_전체_조회() {
        webTestClient.get().uri("/articles")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$..contents").value(hasItem(CONTENTS))
                .jsonPath("$..imageUrl").value(hasItem(IMAGE_URL))
                .jsonPath("$..videoUrl").value(hasItem(VIDEO_URL))
        ;
    }

    @Test
    void 게시글_정상_작성() {
        ArticleFeature articleFeature = new ArticleFeature(CONTENTS, IMAGE_URL, VIDEO_URL);
        webTestClient.post().uri("/articles")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(articleFeature), ArticleFeature.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$..contents").isEqualTo(CONTENTS)
                .jsonPath("$..imageUrl").isEqualTo(IMAGE_URL)
                .jsonPath("$..videoUrl").isEqualTo(VIDEO_URL)
                ;
    }

    @Test
    void 게시글_업데이트() {
        ArticleFeature updatedArticleFeature = new ArticleFeature(UPDATE_CONTENTS, UPDATE_IMAGE_URL, UPDATE_VIDEO_URL);
        webTestClient.put().uri("/articles/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(updatedArticleFeature), ArticleFeature.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$..contents").isEqualTo(UPDATE_CONTENTS)
                .jsonPath("$..imageUrl").isEqualTo(UPDATE_IMAGE_URL)
                .jsonPath("$..videoUrl").isEqualTo(UPDATE_VIDEO_URL)
                ;
    }
}
