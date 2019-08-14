package com.wootecobook.turkey.user.controller.api;

import com.wootecobook.turkey.user.controller.BaseControllerTest;
import com.wootecobook.turkey.user.service.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.wootecobook.turkey.user.service.exception.NotFoundUserException.NOT_FOUND_USER_MESSAGE;
import static com.wootecobook.turkey.user.service.exception.SignUpException.SIGN_UP_FAIL_MESSAGE;
import static com.wootecobook.turkey.user.service.exception.UserDeleteException.USER_DELETE_FAIL_MESSAGE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest extends BaseControllerTest {

    private static final String VALID_EMAIL = "email@test.test";
    private static final String VALID_NAME = "name";
    private static final String VALID_PASSWORD = "passWORD1!";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 유저_생성() {
        UserRequest userRequest = UserRequest.builder()
                .email(VALID_EMAIL)
                .name(VALID_NAME)
                .password(VALID_PASSWORD)
                .build();

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userRequest), UserRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.email").isEqualTo(VALID_EMAIL)
                .jsonPath("$.name").isEqualTo(VALID_NAME);
    }

    @Test
    void 유저_생성_이메일_에러() {
        UserRequest userRequest = UserRequest.builder()
                .email("test")
                .name(VALID_NAME)
                .password(VALID_PASSWORD)
                .build();

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userRequest), UserRequest.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.errorMessage").isNotEmpty()
                .jsonPath("$.errorMessage").isEqualTo(SIGN_UP_FAIL_MESSAGE);
    }

    @Test
    void 유저_생성_이름_에러() {
        UserRequest userRequest = UserRequest.builder()
                .email(VALID_EMAIL)
                .name("1")
                .password(VALID_PASSWORD)
                .build();

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userRequest), UserRequest.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.errorMessage").isNotEmpty()
                .jsonPath("$.errorMessage").isEqualTo(SIGN_UP_FAIL_MESSAGE);
    }

    @Test
    void 유저_생성_비밀번호_에러() {
        UserRequest userRequest = UserRequest.builder()
                .email(VALID_EMAIL)
                .name(VALID_NAME)
                .password("1")
                .build();

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userRequest), UserRequest.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.errorMessage").isNotEmpty()
                .jsonPath("$.errorMessage").isEqualTo(SIGN_UP_FAIL_MESSAGE);
    }

    @Test
    void 유저_조회() {
        Long id = addUser("show", "show@show.show", VALID_PASSWORD);

        webTestClient.get()
                .uri("/api/users/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.email").isEqualTo("show@show.show")
                .jsonPath("$.name").isEqualTo("show");
    }

    @Test
    void 없는_유저_조회() {
        webTestClient.get()
                .uri("/api/users/" + Long.MAX_VALUE)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.errorMessage").isNotEmpty()
                .jsonPath("$.errorMessage").isEqualTo(NOT_FOUND_USER_MESSAGE);
    }


    @Test
    void 유저_삭제() {
        Long id = addUser("delete", "delete@delete.del", VALID_PASSWORD);

        webTestClient.delete()
                .uri("/api/users/" + id)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 없는_유저_삭제() {
        webTestClient.delete()
                .uri("/api/users/" + Long.MAX_VALUE)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.errorMessage").isNotEmpty()
                .jsonPath("$.errorMessage").isEqualTo(USER_DELETE_FAIL_MESSAGE);
    }
}