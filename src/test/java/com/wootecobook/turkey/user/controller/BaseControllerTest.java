package com.wootecobook.turkey.user.controller;

import com.wootecobook.turkey.user.service.dto.UserRequest;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public abstract class BaseControllerTest {
    @Autowired
    WebTestClient webTestClient;

    /**
     * 회원 가입
     *
     * @param name
     * @param email
     * @param password
     * @return @Id
     */
    public Long addUser(String name, String email, String password) {
        UserRequest userRequest = UserRequest.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        EntityExchangeResult<UserResponse> result = webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userRequest), UserRequest.class)
                .exchange()
                .expectBody(UserResponse.class)
                .returnResult();

        return result.getResponseBody().getId();
    }

    protected void deleteUser(Long id) {
        webTestClient.delete()
                .uri("/api/users/" + id)
                .exchange()
                .expectStatus().isOk();
    }
}
