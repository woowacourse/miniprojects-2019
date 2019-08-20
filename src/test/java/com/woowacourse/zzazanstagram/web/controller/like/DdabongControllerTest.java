package com.woowacourse.zzazanstagram.web.controller.like;

import com.woowacourse.zzazanstagram.model.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.woowacourse.zzazanstagram.model.article.ArticleConstant.*;
import static org.slf4j.LoggerFactory.getLogger;

class DdabongControllerTest extends RequestTemplate {
    private static final Logger log = getLogger(DdabongControllerTest.class);

    @Test
    void 좋아요_누르기() {
        createArticle();

        postHeaderWithLogin("/articles/" + "1" + "/ddabongs")
                .exchange()
                .expectStatus().isOk();
    }

    private WebTestClient.ResponseSpec createArticle() {
        return postHeaderWithLogin("/articles")
                .body(BodyInserters.fromFormData("image", IMAGE_URL)
                        .with("contents", CONTENTS)
                        .with("hashTag", HASHTAG))
                .exchange();
    }
}