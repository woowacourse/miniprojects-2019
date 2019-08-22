package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


public class ArticleApiControllerTests extends AbstractControllerTest {

    private String cookie;
    private String anotherCookie;
    private long articleId;
    private SignUpUserRequest signUpUserRequest;

    @BeforeEach
    void setUp() {
        signUpUserRequest = createSignUpUser();

        cookie = getCookieAfterSignUpAndLogin(signUpUserRequest);
        articleId = saveArticle(cookie);

        signUpUserRequest = createSignUpUser();
        anotherCookie = getCookieAfterSignUpAndLogin(signUpUserRequest);

    }

    @Test
    @DisplayName("게시글 생성 성공")
    void save() {
        requestWithBodyBuilder(createMultipartBodyBuilder(), HttpMethod.POST, "/api/articles", cookie)
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("파일 조회 성공")
    void read() {

        webTestClient.get().uri("/api/articles/" + articleId + "/file")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void update() {
        ArticleEditRequest articleEditRequest = new ArticleEditRequest("update contents");

        webTestClient.put().uri("/api/articles/" + articleId)
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(articleEditRequest), ArticleEditRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get().uri("/articles/" + articleId)
                .header("Cookie", cookie)
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
        ArticleEditRequest articleEditRequest = new ArticleEditRequest("update contents");

        webTestClient.put().uri("/api/articles/" + articleId)
                .header("Cookie", anotherCookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(articleEditRequest), ArticleEditRequest.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void delete_by_Not_Author() {
        webTestClient.delete().uri("/api/articles/" + articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get().uri("/articles/" + articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("다른 사용자에 의한 게시글 삭제 실패")
    void delete() {
        webTestClient.delete().uri("/api/articles/" + articleId)
                .header("Cookie", anotherCookie)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}
