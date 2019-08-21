package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


public class ArticleApiControllerTests extends AbstractControllerTest {

    private SignUpUserRequest signUpUserRequest;
    private String sessionCookie;

    @BeforeEach
    void setUp() {
        signUpUserRequest = signUpUserRequest.builder()
                .userName("김버디")
                .email(AUTO_INCREMENT + "buddy@buddy.com")
                .nickName(AUTO_INCREMENT + "buddy")
                .password("buddybuddy1!")
                .build();

        // 회원가입
        defaultSignUp(signUpUserRequest, true)
                .expectStatus().isOk();

        // 로그인
        AuthUserRequest authUserRequest = new AuthUserRequest(signUpUserRequest.getEmail(), signUpUserRequest.getPassword());
        sessionCookie = getCookie(authUserRequest);
    }

    @Test
    @DisplayName("게시글 생성 성공")
    void save() {
        requestWithBodyBuilder(createMultipartBodyBuilder(), HttpMethod.POST, "/api/articles")
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("파일 조회 성공")
    void read() {
        Long articleId = saveArticle();
        webTestClient.get().uri("/api/articles/" + articleId + "/file")
                .header("Cookie", sessionCookie)
                .exchange()
                .expectStatus()
                .isOk()
        ;
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void update() {
        long id = saveArticle();
        ArticleEditRequest articleEditRequest = new ArticleEditRequest("update contents");

        webTestClient.put().uri("/api/articles/" + id)
                .header("Cookie", sessionCookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(articleEditRequest), ArticleEditRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get().uri("/articles/" + id)
                .header("Cookie", sessionCookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(Objects.requireNonNull(res.getResponseBody()));
                    assertThat(body.contains(articleEditRequest.getContents())).isTrue();
                });
    }

    @Test
    @DisplayName("다른 사용자에 의한 게시글 수정 실패")
    void update_By_Not_Author() {
        long id = saveArticle();
        ArticleEditRequest articleEditRequest = new ArticleEditRequest("update contents");

        webTestClient.put().uri("/api/articles/" + id)
                .header("Cookie", getAnotherCookie())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(articleEditRequest), ArticleEditRequest.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void delete_by_Not_Author() {
        long id = saveArticle();

        webTestClient.delete().uri("/api/articles/" + id)
                .header("Cookie", getAnotherCookie())
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("다른 사용자에 의한 게시글 삭제 실패")
    void delete() {
        long id = saveArticle();

        webTestClient.delete().uri("/api/articles/" + id)
                .header("Cookie", sessionCookie)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get().uri("/articles/" + id)
                .header("Cookie", sessionCookie)
                .exchange()
                .expectStatus().isBadRequest();
    }

    private MultipartBodyBuilder createMultipartBodyBuilder() {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", new ByteArrayResource(new byte[]{1, 2, 3, 4}) {
            @Override
            public String getFilename() {
                return "catImage.jpeg";
            }
        }, MediaType.IMAGE_JPEG);
        bodyBuilder.part("contents", "contents");
        bodyBuilder.part("hashtag", "hashtag");
        return bodyBuilder;
    }

    private WebTestClient.ResponseSpec requestWithBodyBuilder(MultipartBodyBuilder bodyBuilder, HttpMethod requestMethod, String requestUri) {
        return webTestClient.method(requestMethod)
                .uri(requestUri)
                .header("Cookie", sessionCookie)
                .body(BodyInserters.fromObject(bodyBuilder.build()))
                .exchange();
    }

    private long saveArticle() {
        final long[] articleId = new long[1];

        requestWithBodyBuilder(createMultipartBodyBuilder(), HttpMethod.POST, "/api/articles")
                .expectBody()
                .jsonPath("$.id")
                .value(id -> articleId[0] = Long.parseLong(id.toString()));

        return articleId[0];
    }

    private String getAnotherCookie() {
        SignUpUserRequest anotherUser = new SignUpUserRequest(AUTO_INCREMENT + "exception", "buddy", "1234", AUTO_INCREMENT + "exception@gmail.com");
        defaultSignUp(anotherUser, true);
        return getCookie(new AuthUserRequest(anotherUser.getEmail(), anotherUser.getPassword()));

    }


}
