package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.dto.UserSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.woowacourse.edd.exceptions.PasswordNotMatchException.PASSWORD_NOT_MATCH_MESSAGE;
import static com.woowacourse.edd.exceptions.UnauthenticatedException.UNAUTHENTICATED_MESSAGE;
import static com.woowacourse.edd.exceptions.UserNotFoundException.USER_NOT_FOUND_MESSAGE;
import static com.woowacourse.edd.presentation.controller.LoginController.LOGOUT_URL;
import static com.woowacourse.edd.presentation.controller.LoginController.LOOKUP_URL;

public class LoginControllerTests extends BasicControllerTests {

    @Test
    void login() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(DEFAULT_LOGIN_EMAIL, DEFAULT_LOGIN_PASSWORD);
        requestLogin(loginRequestDto).expectStatus().isOk();
    }

    @Test
    void login_invalid_password() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(DEFAULT_LOGIN_EMAIL, DEFAULT_LOGIN_PASSWORD + "1");
        assertFailBadRequest(requestLogin(loginRequestDto), PASSWORD_NOT_MATCH_MESSAGE);
    }

    @Test
    void login_invalid_email() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(DEFAULT_LOGIN_EMAIL + "conas", DEFAULT_LOGIN_PASSWORD);
        assertFailNotFound(requestLogin(loginRequestDto), USER_NOT_FOUND_MESSAGE);
    }

    @Test
    void login_unavailable_user() {
        String testEmail = "edan@gmail.com";
        String testPassword = "p@ssW0rd";

        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("edan", testEmail, testPassword, testPassword);
        String url = signUp(userSaveRequestDto).getResponseHeaders().getLocation().toASCIIString();

        String sid = getLoginCookie(new LoginRequestDto("edan@gmail.com", "p@ssW0rd"));

        webTestClient.delete().uri(url)
            .cookie(COOKIE_JSESSIONID, sid)
            .exchange()
            .expectStatus().isNoContent();

        LoginRequestDto loginRequestDto = new LoginRequestDto(testEmail, testPassword);
        assertFailNotFound(requestLogin(loginRequestDto), USER_NOT_FOUND_MESSAGE);
    }

    @Test
    void logout() {
        executePost(LOGOUT_URL).cookie(COOKIE_JSESSIONID, getDefaultLoginSessionId())
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("현재 로그인된 사용자 정보 조회")
    void lookup() {
        executeGet(LOOKUP_URL).cookie(COOKIE_JSESSIONID, getDefaultLoginSessionId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo(DEFAULT_LOGIN_NAME)
            .jsonPath("$.id").isEqualTo(DEFAULT_LOGIN_ID);
    }

    @Test
    @DisplayName("비로그인 상태에서 로그인 정보 조회")
    void lookup_fail() {
        WebTestClient.ResponseSpec responseSpec = executeGet(LOOKUP_URL)
            .exchange();

        assertFailUnauthorized(responseSpec, UNAUTHENTICATED_MESSAGE);
    }
}
