package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.EddApplicationTests;
import com.woowacourse.edd.application.dto.UserRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.edd.presentation.controller.UserController.USER_URL;

public class UserControllerTests extends EddApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void user_save() {
        UserRequestDto userSaveRequestDto = new UserRequestDto("robby", "shit@email.com", "P@ssW0rd");
        EntityExchangeResult<byte[]> result = webTestClient.post()
            .uri(USER_URL)
            .body(Mono.just(userSaveRequestDto), UserRequestDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectHeader().valueMatches("Location", USER_URL + "/\\d")
            .expectBody()
            .returnResult();

        webTestClient
            .get()
            .uri(result.getResponseHeaders().getLocation().toASCIIString())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("robby")
            .jsonPath("$.email").isEqualTo("shit@email.com");
    }

    @Test
    void user_update() {
        UserRequestDto userRequestDto = new UserRequestDto("jm", "hansome@gmail.com", "P!ssW0rd");

        webTestClient.put()
            .uri(USER_URL + "/1")
            .body(Mono.just(userRequestDto), UserRequestDto.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo(userRequestDto.getName())
            .jsonPath("$.email").isEqualTo(userRequestDto.getEmail());
    }

    @Test
    void user_delete_not_found() {
        webTestClient.delete()
            .uri(USER_URL + "/999")
            .exchange()
            .expectStatus()
            .isNotFound();  //404
    }

    @Test
    void user_delete_no_content() {
        webTestClient.delete()
            .uri(USER_URL + "/1")
            .exchange()
            .expectStatus()
            .isNoContent();
    }

    @Test
    void user_delete_when_already_deleted() {
        UserRequestDto userRequestDto = new UserRequestDto("conas", "conas@email.com", "P@ssW0rd");

        EntityExchangeResult<byte[]> result = webTestClient
            .post()
            .uri(USER_URL)
            .body(Mono.just(userRequestDto), UserRequestDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectHeader().valueMatches("Location", USER_URL + "/\\d")
            .expectBody()
            .returnResult();

        String url = result.getResponseHeaders().getLocation().toASCIIString();
        webTestClient.delete()
            .uri(url)
            .exchange()
            .expectStatus()
            .isNoContent();

        webTestClient.delete()
            .uri(url)
            .exchange()
            .expectStatus()
            .isNotFound();
    }
}
