package com.wootecobook.turkey.login.controller;

import com.wootecobook.turkey.BaseControllerTests;
import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.login.service.dto.LoginRequest;
import com.wootecobook.turkey.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.wootecobook.turkey.login.service.exception.LoginFailException.LOGIN_FAIL_MESSAGE;
import static com.wootecobook.turkey.user.domain.User.INVALID_PASSWORD_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginApiControllerTests extends BaseControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    private Long userId;

    @BeforeEach
    void setUp() {
        userId = addUser(VALID_USER_NAME, VALID_USER_EMAIL, VALID_USER_PASSWORD);
    }

    @Test
    void 로그인() {
        //given
        LoginRequest loginRequest = LoginRequest.builder()
                .email(VALID_USER_EMAIL)
                .password(VALID_USER_PASSWORD)
                .build();

        //when & then
        webTestClient.post()
                .uri("/login")
                .contentType(MEDIA_TYPE)
                .accept(MEDIA_TYPE)
                .body(Mono.just(loginRequest), LoginRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 없는_email로_로그인() {
        //given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("invalid@invalid.invalid")
                .password(VALID_USER_PASSWORD)
                .build();

        //when
        ErrorMessage errorMessage = webTestClient.post()
                .uri("/login")
                .contentType(MEDIA_TYPE)
                .accept(MEDIA_TYPE)
                .body(Mono.just(loginRequest), LoginRequest.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MEDIA_TYPE)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(errorMessage.getMessage()).contains(LOGIN_FAIL_MESSAGE, UserService.NOT_FOUND_MESSAGE);
    }

    @Test
    void 잘못된_비밀번호로_로그인() {
        //given
        LoginRequest loginRequest = LoginRequest.builder()
                .email(VALID_USER_EMAIL)
                .password("invalid")
                .build();

        //when
        ErrorMessage errorMessage = webTestClient.post()
                .uri("/login")
                .contentType(MEDIA_TYPE)
                .accept(MEDIA_TYPE)
                .body(Mono.just(loginRequest), LoginRequest.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MEDIA_TYPE)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(errorMessage.getMessage()).contains(LOGIN_FAIL_MESSAGE, INVALID_PASSWORD_MESSAGE);
    }

    @Test
    void 로그아웃() {
        //when & then
        webTestClient.post()
                .uri("/logout")
                .cookie(JSESSIONID, logIn(VALID_USER_EMAIL, VALID_USER_PASSWORD))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 로그인_안된_상태에서_로그아웃() {
        //when & then
        webTestClient.post()
                .uri("/logout")
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("Location", ".*/users/form");
    }

    @AfterEach
    void tearDown() {
        deleteUser(userId, VALID_USER_EMAIL, VALID_USER_PASSWORD);
    }

}