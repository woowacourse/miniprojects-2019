package com.woowacourse.dsgram.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArticleControllerTests extends AbstractControllerTest {

    private String cookie;

    @BeforeEach
    void setUp() {
//        SignUpUserRequest signUpUserRequest = new SignUpUserRequest(AUTO_INCREMENT + "test", "test", "1234", AUTO_INCREMENT + "test2@test.com");
//        getResponseAfterSignUp(signUpUserRequest, true);
//        cookie = loginAndGetCookie(new AuthUserRequest(signUpUserRequest.getEmail(), signUpUserRequest.getPassword()));

        cookie = getCookieAfterSignUpAndLogin();
    }

    @Test
    void 로그인후_글작성시_article_edit_페이지로_이동_성공() {
        webTestClient.get()
                .uri("/articles/writing")
                .header("cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
