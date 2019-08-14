package com.woowacourse.zzazanstagram.model.member;

import com.woowacourse.zzazanstagram.model.support.WebTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 회원가입_페이지_이동() {
        webTestClient.get().uri("/signup")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void 회원가입_성공() {
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
    }

    @Test
    void 로그인_성공() {
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

        webTestClient.post().uri("/login")
                .body(WebTestHelper.loginForm("test@gmail.com",
                        "Password!1"))
                .exchange()
                .expectHeader()
                .valueMatches("location", ".*/;jsessionid=([\\d\\w]+)")
                .expectStatus()
                .isFound();
    }
}