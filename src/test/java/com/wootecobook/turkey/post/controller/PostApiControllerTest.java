package com.wootecobook.turkey.post.controller;

import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
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

    @Test
    void 페이지_조회_정상_로직_테스트() {
        webTestClient.get().uri(POST_URL)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 게시글_수정_정상_로직_테스트() {
        PostRequest postRequest = new PostRequest("olaf");
        Long postId = addPost(postRequest);

        PostRequest postUpdateRequest = new PostRequest("chelsea");
        webTestClient.put().uri(POST_URL + "/" + postId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(postUpdateRequest), PostRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.contents.contents").isEqualTo("chelsea");
    }

    @Test
    void 게시글_수정_contents가_빈_내용인_경우_예외_테스트() {
        PostRequest postRequest = new PostRequest("olaf");
        Long postId = addPost(postRequest);

        PostRequest postUpdateRequest = new PostRequest("");
        webTestClient.put().uri(POST_URL + "/" + postId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(postUpdateRequest), PostRequest.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void 게시글_삭제_정상_로직_테스트() {
        PostRequest postRequest = new PostRequest("olaf");
        Long postId = addPost(postRequest);

        webTestClient.delete().uri(POST_URL + "/" + postId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 게시글_삭제_존재하지_않는_게시글_예외_테스트() {
        PostRequest postRequest = new PostRequest("olaf");
        Long postId = addPost(postRequest) + 1;

        webTestClient.delete().uri(POST_URL + "/" + postId)
                .exchange()
                .expectStatus().isBadRequest();
    }

    private Long addPost(PostRequest postRequest) {
        PostResponse postResponse = webTestClient.post().uri(POST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(postRequest), PostRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();

        return postResponse.getId();
    }
}