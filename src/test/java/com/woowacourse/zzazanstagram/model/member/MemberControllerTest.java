package com.woowacourse.zzazanstagram.model.member;

import com.woowacourse.zzazanstagram.model.support.WebTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("회원가입 페이지 이동")
    void signUpForm() {
        webTestClient.get().uri("/signup")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp() {
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
}