package com.wootecobook.turkey.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void 유저_생성() {
        webTestClient.post()
                .uri("/users")
                .body(BodyInserters
                        .fromFormData("email", "email@test.test")
                        .with("name", "name")
                        .with("password", "passWORD1!"))
                .exchange()
                .expectStatus().isFound();
    }
}