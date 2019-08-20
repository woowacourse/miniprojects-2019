package com.woowacourse.dsgram.web.controller;

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

    private Long saveArticle() {
        Long[] articleId = new Long[1];

        requestWithBodyBuilder(createMultipartBodyBuilder(), HttpMethod.POST, "/api/articles")
                .expectBody()
                .jsonPath("$.id")
                .value(o -> articleId[0] = Long.parseLong(o.toString()));

        return articleId[0];
    }

}
