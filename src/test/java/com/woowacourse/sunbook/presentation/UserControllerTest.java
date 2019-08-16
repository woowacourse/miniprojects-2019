package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.presentation.template.TestTemplate;
import org.junit.jupiter.api.Test;

public class UserControllerTest extends TestTemplate {

    @Test
    void 로그인_회원가입_페이지_이동() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                ;
    }

    @Test
    void 메인_페이지_이동() {
        webTestClient.get().uri("/sunbook")
                .exchange()
                .expectStatus()
                .isOk()
                ;
    }

    @Test
    void 로그아웃_성공() {
        webTestClient.post().uri("/signout")
                .exchange()
                .expectStatus()
                .isFound()
                ;
    }
}
