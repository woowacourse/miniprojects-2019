package com.wootecobook.turkey.post.controller;

import com.wootecobook.turkey.post.service.dto.PostRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {

    public static final String POST_URL = "/api/posts";
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void post_생성_정상_로직_테스트() {
        PostRequest postRequest = new PostRequest("contents");

        webTestClient.post().uri(POST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(postRequest), PostRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.contents.contents").isEqualTo("contents");
    }

    @Test
    void post_생성_contents가_빈값인_경우_예외_테스트() {
        PostRequest postRequest = new PostRequest("");

        webTestClient.post().uri(POST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(postRequest), PostRequest.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").exists();
    }
}