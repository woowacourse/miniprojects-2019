package com.woowacourse.dsgram.web.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 글작성시_article_edit_페이지로_이동_성공() {
        webTestClient.get()
                .uri("/articles/writing")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
