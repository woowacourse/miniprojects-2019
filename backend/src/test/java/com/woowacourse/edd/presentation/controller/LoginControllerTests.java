package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.dto.UserRequestDto;
import org.junit.jupiter.api.Test;

import static com.woowacourse.edd.exceptions.PasswordNotMatchException.PASSWORD_NOT_MATCH_MESSAGE;
import static com.woowacourse.edd.exceptions.UserNotFoundException.USER_NOT_FOUND_MESSAGE;
import static com.woowacourse.edd.presentation.controller.LoginController.LOGOUT_URL;

public class LoginControllerTests extends BasicControllerTests {

    @Test
    void login() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(DEFAULT_LOGIN_EMAIL, DEFAULT_LOGIN_PASSWORD);
        requestLogin(loginRequestDto).isOk();
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

        UserRequestDto userRequestDto = new UserRequestDto("edan", testEmail, testPassword);
        String url = signUp(userRequestDto).getResponseHeaders().getLocation().toASCIIString();

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
}
