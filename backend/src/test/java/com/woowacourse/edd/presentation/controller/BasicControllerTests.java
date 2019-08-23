package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.dto.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.edd.presentation.controller.LoginController.LOGIN_URL;
import static com.woowacourse.edd.presentation.controller.UserController.USER_URL;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicControllerTests {

    protected static final Long DEFAULT_LOGIN_ID = 1L;
    protected static final String DEFAULT_LOGIN_NAME = "robby";
    protected static final String DEFAULT_LOGIN_EMAIL = "kangmin789@naver.com";
    protected static final String DEFAULT_LOGIN_PASSWORD = "P@ssW0rd";
    protected static final String COOKIE_JSESSIONID = "JSESSIONID";

    protected static final Long DEFAULT_VIDEO_ID = 1L;

    @Autowired
    protected WebTestClient webTestClient;

    protected String getDefaultLoginSessionId() {
        return getLoginCookie(new LoginRequestDto(DEFAULT_LOGIN_EMAIL, DEFAULT_LOGIN_PASSWORD));
    }

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

    protected void assertFailUnauthorized(StatusAssertions statusAssertions, String errorMessage) {
        WebTestClient.BodyContentSpec bodyContentSpec = statusAssertions
            .isUnauthorized()
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

    protected StatusAssertions requestLogin(LoginRequestDto loginRequestDto) {
        return executePost(LOGIN_URL).body(Mono.just(loginRequestDto), LoginRequestDto.class)
            .exchange()
            .expectStatus();
    }

    protected String getLoginCookie(LoginRequestDto loginRequestDto) {
        return requestLogin(loginRequestDto).isOk()
            .expectBody()
            .returnResult()
            .getResponseCookies()
            .getFirst(COOKIE_JSESSIONID)
            .getValue();
    }
}
