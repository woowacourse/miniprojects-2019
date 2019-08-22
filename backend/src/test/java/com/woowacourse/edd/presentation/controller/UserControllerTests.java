package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.UserRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import reactor.core.publisher.Mono;

import static com.woowacourse.edd.presentation.controller.UserController.USER_URL;

public class UserControllerTests extends BasicControllerTests {

    @Test
    void user_save() {
        UserRequestDto userSaveRequestDto = new UserRequestDto("robby", "shit@email.com", "P@ssW0rd");
        EntityExchangeResult<byte[]> result = signUp(userSaveRequestDto);

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
        UserRequestDto userSaveRequestDto = new UserRequestDto("robby", "shit123@email.com", "P@ssW0rd");
        UserRequestDto userRequestDto = new UserRequestDto("jm", "hansome@gmail.com", "P!ssW0rd");

        EntityExchangeResult<byte[]> result = signUp(userSaveRequestDto);
        String uri = result.getResponseHeaders().getLocation().toASCIIString();

        webTestClient.put()
            .uri(uri)
            .cookie(COOKIE_JSESSIONID, getDefaultLoginSessionId())
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
            .cookie(COOKIE_JSESSIONID, getDefaultLoginSessionId())
            .exchange()
            .expectStatus()
            .isNotFound();  //404
    }

    @Test
    void user_delete_no_content() {
        UserRequestDto userSaveRequestDto = new UserRequestDto("robby", "shit222@email.com", "P@ssW0rd");
        EntityExchangeResult<byte[]> result = signUp(userSaveRequestDto);
        String url = result.getResponseHeaders().getLocation().toASCIIString();
        webTestClient.delete()
            .uri(url)
            .cookie(COOKIE_JSESSIONID, getDefaultLoginSessionId())
            .exchange()
            .expectStatus()
            .isNoContent();
    }

    @Test
    void user_delete_when_already_deleted() {
        UserRequestDto userRequestDto = new UserRequestDto("conas", "conas@email.com", "P@ssW0rd");

        EntityExchangeResult<byte[]> result = signUp(userRequestDto);
        String url = result.getResponseHeaders().getLocation().toASCIIString();
        String jsessionid = getDefaultLoginSessionId();

        webTestClient.delete()
            .uri(url)
            .cookie(COOKIE_JSESSIONID, jsessionid)
            .exchange()
            .expectStatus()
            .isNoContent();

        webTestClient.delete()
            .uri(url)
            .cookie(COOKIE_JSESSIONID, jsessionid)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void find_by_id() {
        webTestClient.get().uri(USER_URL + "/1")
            .exchange()
            .expectStatus().isOk();
    }
}
