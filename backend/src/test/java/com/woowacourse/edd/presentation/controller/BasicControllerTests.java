package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.edd.presentation.controller.UserController.USER_URL;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicControllerTests {

    @Autowired
    protected WebTestClient webTestClient;

    protected void assertFailBadRequest(StatusAssertions statusAssertions, String errorMessage) {
        WebTestClient.BodyContentSpec bodyContentSpec = statusAssertions
            .isBadRequest()
            .expectBody();

        checkErrorResponse(bodyContentSpec, errorMessage);
    }

    protected void assertFailNotFound(StatusAssertions statusAssertions, String errorMessage) {
        WebTestClient.BodyContentSpec bodyContentSpec = statusAssertions
            .isNotFound()
            .expectBody();

        checkErrorResponse(bodyContentSpec, errorMessage);
    }

    protected void checkErrorResponse(WebTestClient.BodyContentSpec bodyContentSpec, String errorMessage) {
        bodyContentSpec.jsonPath("$.result").isEqualTo("FAIL")
            .jsonPath("$.message").isEqualTo(errorMessage);
    }

    protected WebTestClient.RequestHeadersSpec<?> executeGet(String uri) {
        return webTestClient.get().uri(uri);
    }

    protected WebTestClient.RequestBodySpec executePut(String uri) {
        return webTestClient.put().uri(uri);
    }

    protected WebTestClient.RequestBodySpec executePost(String uri) {
        return webTestClient.post().uri(uri);
    }

    protected WebTestClient.RequestHeadersSpec executeDelete(String uri) {
        return webTestClient.delete().uri(uri);
    }

    protected EntityExchangeResult<byte[]> signUp(UserRequestDto userSaveRequestDto) {
        return webTestClient.post()
            .uri(USER_URL)
            .body(Mono.just(userSaveRequestDto), UserRequestDto.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectHeader().valueMatches("Location", USER_URL + "/\\d")
            .expectBody()
            .returnResult();
    }
}
