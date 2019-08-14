package com.wootecobook.turkey.user.controller;

import com.wootecobook.turkey.user.service.dto.UserRequest;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public abstract class BaseControllerTest {

    private static final String USER_API_URI = "/api/users";
    private static final String USER_API_URI_WITH_SLASH = USER_API_URI + "/";

    @Autowired
    WebTestClient webTestClient;

    public Long addUser(String name, String email, String password) {
        UserRequest userRequest = UserRequest.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        UserResponse result = webTestClient.post()
                .uri(USER_API_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userRequest), UserRequest.class)
                .exchange()
                .expectBody(UserResponse.class)
                .returnResult()
                .getResponseBody();

        return result.getId();
    }

    protected void deleteUser(Long id) {
        webTestClient.delete()
                .uri(USER_API_URI_WITH_SLASH + id)
                .exchange()
                .expectStatus().isOk();
    }
}
