package com.wootecobook.turkey.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    private static final String VALID_EMAIL = "email@test.test";
    private static final String VALID_NAME = "name";
    private static final String VALID_PASSWORD = "passWORD1!";


    @Autowired
    WebTestClient webTestClient;

    @Test
    void 유저_생성() {
        webTestClient.post()
                .uri("/users")
                .body(BodyInserters
                        .fromFormData("email", VALID_EMAIL)
                        .with("name", VALID_NAME)
                        .with("password", VALID_PASSWORD))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("location", ".*/");
    }

    @Test
    void 유저_생성_이메일_에러() {
        webTestClient.post()
                .uri("/users")
                .body(BodyInserters
                        .fromFormData("email", "emailtest.test")
                        .with("name", VALID_NAME)
                        .with("password", VALID_PASSWORD))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("location", ".*/users/form");
    }

    @Test
    void 유저_생성_이름_에러() {
        webTestClient.post()
                .uri("/users")
                .body(BodyInserters
                        .fromFormData("email", VALID_EMAIL)
                        .with("name", "n")
                        .with("password", VALID_PASSWORD))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("location", ".*/users/form");
    }

    @Test
    void 유저_생성_비밀번호_에러() {
        webTestClient.post()
                .uri("/users")
                .body(BodyInserters
                        .fromFormData("email", VALID_EMAIL)
                        .with("name", VALID_NAME)
                        .with("password", "aa"))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("location", ".*/users/form");
    }
}