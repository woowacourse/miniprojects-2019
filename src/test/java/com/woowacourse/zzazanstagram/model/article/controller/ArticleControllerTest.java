package com.woowacourse.zzazanstagram.model.article.controller;

import com.woowacourse.zzazanstagram.model.support.WebTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.woowacourse.zzazanstagram.model.article.ArticleConstant.*;
import static com.woowacourse.zzazanstagram.model.support.WebTestHelper.loginForm;

@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private String cookie;

    @BeforeEach
    void setUp() {
        webTestClient.post().uri("/members")
                .body(WebTestHelper.userSignUpForm("test@gmail.com",
                        "myName",
                        "https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-600w-1048185397.jpg",
                        "myNick",
                        "Password!1"))
                .exchange()
                .expectHeader()
                .valueMatches("location", ".*/login")
                .expectStatus()
                .isFound();

        cookie = getCookie("test@gmail.com");
    }

    @Test
    void 게시글_등록_페이지_이동_테스트() {
        webTestClient.get().uri("/articles/new")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 게시글_등록이_되는지_테스트() {
        webTestClient.post().uri("/articles")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("imageUrl", IMAGE_URL)
                        .with("contents", CONTENTS)
                        .with("hashTag", HASHTAG))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", "http://localhost:[0-9]+/");
    }

    private String getCookie(String email) {
        return webTestClient.post().uri("/login")
                .body(loginForm(email, "Password!1"))
                .exchange()
                .expectStatus()
                .isFound()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }

}