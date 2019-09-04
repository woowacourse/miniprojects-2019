package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.dto.UserSaveRequestDto;
import com.woowacourse.edd.application.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_EMAIL_FORM_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_EMAIL_SIZE_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_NAME_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_PASSWORD_CONFIRM_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_PASSWORD_MESSAGE;
import static com.woowacourse.edd.exceptions.DuplicateEmailSignUpException.DUPLICATE_EMAIL_SIGNUP_MESSAGE;
import static com.woowacourse.edd.presentation.controller.UserController.USER_URL;

public class UserControllerTests extends BasicControllerTests {

    public static final String DEFAULT_USER_NAME = "robby";
    public static final String DEFAULT_USER_PASSWORD = "P@ssW0rd";

    @Test
    void email_validation_not_email_name() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("robby", "@gmail.com", "P@ssW0rd", "P@ssW0rd");
        assertFailBadRequest(assertRequestValidation(userSaveRequestDto), INVALID_EMAIL_FORM_MESSAGE);
    }

    @Test
    void email_validation_not_at() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("robby", "asdadasdasd", "P@ssW0rd", "P@ssW0rd");
        assertFailBadRequest(assertRequestValidation(userSaveRequestDto), INVALID_EMAIL_FORM_MESSAGE);
    }

    @Test
    void email_validation_not_dot() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("robby", "asdadasdasd@gmail", "P@ssW0rd", "P@ssW0rd");
        assertFailBadRequest(assertRequestValidation(userSaveRequestDto), INVALID_EMAIL_FORM_MESSAGE);
    }

    @Test
    void name_validation() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("r", "als5610@naver.com", DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);
        assertFailBadRequest(assertRequestValidation(userSaveRequestDto), INVALID_NAME_MESSAGE);
    }

    @Test
    void password_validation() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(DEFAULT_USER_NAME, "als5610@naver.com", "Passw0rd", "Passw0rd");
        assertFailBadRequest(assertRequestValidation(userSaveRequestDto), INVALID_PASSWORD_MESSAGE);
    }

    @Test
    void password_confirmation() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(DEFAULT_USER_NAME, "towa4237@example.com", DEFAULT_USER_PASSWORD, "p@ssW0rd");
        assertFailBadRequest(assertRequestValidation(userSaveRequestDto), INVALID_PASSWORD_CONFIRM_MESSAGE);
    }

    @Test
    void user_save() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(DEFAULT_USER_NAME, "shit@email.com", DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);

        findUser(getUrl(signUp(userSaveRequestDto)))
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo(DEFAULT_USER_NAME)
            .jsonPath("$.email").isEqualTo("shit@email.com");
    }

    @Test
    void user_save_invalid_size() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(DEFAULT_USER_NAME, "als5610@naver.com" + getOverSizeString(256), DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);

        assertFailBadRequest(webTestClient.post()
            .uri(USER_URL)
            .body(Mono.just(userSaveRequestDto), UserSaveRequestDto.class)
            .exchange(), INVALID_EMAIL_SIZE_MESSAGE);
    }

    @Test
    void user_update_invalid_size() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(DEFAULT_USER_NAME, "als5610@naver.com", DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(DEFAULT_USER_NAME, "als5610@naver.com" + getOverSizeString(256));
        LoginRequestDto loginRequestDto = new LoginRequestDto("als5610@naver.com", DEFAULT_USER_PASSWORD);

        String redirectUrl = getUrl(signUp(userSaveRequestDto));
        String cookie = getLoginCookie(loginRequestDto);
        assertFailBadRequest(updateUser(userUpdateRequestDto, redirectUrl, cookie), INVALID_EMAIL_SIZE_MESSAGE);
    }

    @Test
    void user_update() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(DEFAULT_USER_NAME, "shit123@email.com", DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);
        LoginRequestDto loginRequestDto = new LoginRequestDto("shit123@email.com", DEFAULT_USER_PASSWORD);

        String url = getUrl(signUp(userSaveRequestDto));
        String sid = getLoginCookie(loginRequestDto);
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("jm", "hansome@gmail.com");

        updateUser(userUpdateRequestDto, url, sid)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo(userUpdateRequestDto.getName())
            .jsonPath("$.email").isEqualTo(userUpdateRequestDto.getEmail());
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 가입을 시도할 때")
    void duplicate_email_signup() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(DEFAULT_LOGIN_NAME, DEFAULT_LOGIN_EMAIL, DEFAULT_LOGIN_PASSWORD, DEFAULT_LOGIN_PASSWORD);
        WebTestClient.ResponseSpec responseSpec = executePost(USER_URL)
            .body(Mono.just(userSaveRequestDto), UserSaveRequestDto.class)
            .exchange();

        assertFailBadRequest(responseSpec, DUPLICATE_EMAIL_SIGNUP_MESSAGE);
    }

    @Test
    @DisplayName("공백으로 이메일가입을 시도할 때")
    void blank_email_signup() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(DEFAULT_LOGIN_NAME, " ", DEFAULT_LOGIN_PASSWORD, DEFAULT_LOGIN_PASSWORD);
        WebTestClient.ResponseSpec responseSpec = executePost(USER_URL)
            .body(Mono.just(userSaveRequestDto), UserSaveRequestDto.class)
            .exchange();

        assertFailBadRequest(responseSpec, INVALID_EMAIL_FORM_MESSAGE);
    }

    @Test
    @DisplayName("가입된 유저가 다른 유저의 수정을 시도할 때")
    void unauthorized_user_update() {
        UserSaveRequestDto authorizedUser = new UserSaveRequestDto("Jmm", "jm@naver.com", DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);
        UserSaveRequestDto unauthorizedUser = new UserSaveRequestDto("conas", "conas@naver.com", DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);
        UserUpdateRequestDto unauthorizedUpdateRequest = new UserUpdateRequestDto("pobi", "conas@naver.com");

        String url = getUrl(signUp(authorizedUser));
        signUp(unauthorizedUser);
        LoginRequestDto loginRequestDto = new LoginRequestDto("conas@naver.com", DEFAULT_USER_PASSWORD);
        String sid = getLoginCookie(loginRequestDto);

        updateUser(unauthorizedUpdateRequest, url, sid)
            .expectStatus().isForbidden();
    }

    @Test
    @DisplayName("가입되지 않은 유저가 가입되지 않은 유저 삭제를 시도할 때")
    void no_signin_delete_no_signin_user() {
        deleteUser(USER_URL + "/999", null)
            .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("가입되지 않은 유저가 가입된 유저 삭제를 시도할 때")
    void no_sigin_delete_user() {
        deleteUser(USER_URL + "/1", null)
            .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("탈퇴된 이메일로 가입을 시도할 때")
    void no_sigin_delete_try_user_email() {
        String deleteEmail = "delete@email.com";
        String deletePW = DEFAULT_USER_PASSWORD;
        UserSaveRequestDto deleteSaveRequestDto = new UserSaveRequestDto("delete", deleteEmail, deletePW, deletePW);
        String url = signUp(deleteSaveRequestDto).getResponseHeaders().getLocation().toASCIIString();

        deleteUser(url, getLoginCookie(new LoginRequestDto(deleteEmail, deletePW)))
            .expectStatus().isNoContent();

        assertFailBadRequest(executePost(USER_URL)
            .body(Mono.just(deleteSaveRequestDto), UserSaveRequestDto.class)
            .exchange(), DUPLICATE_EMAIL_SIGNUP_MESSAGE);
    }

    @Test
    @DisplayName("가입된 유저가 자신의 유저 삭제를 시도할 때")
    void authorized_user_delete_no_content() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(DEFAULT_USER_NAME, "shit222@email.com", DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);
        LoginRequestDto loginRequestDto = new LoginRequestDto("shit222@email.com", DEFAULT_USER_PASSWORD);
        String url = getUrl(signUp(userSaveRequestDto));
        String sid = getLoginCookie(loginRequestDto);
        deleteUser(url, sid)
            .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("가입된 유저가 다른 유저의 삭제를 시도할 때")
    void unauthorized_user_delete() {
        UserSaveRequestDto authorizedUserDto = new UserSaveRequestDto("normal", "normalUser@gmail.com", DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);
        UserSaveRequestDto unauthorizedUserDto = new UserSaveRequestDto("conas", "conas@gmail.com", DEFAULT_USER_PASSWORD, DEFAULT_USER_PASSWORD);
        LoginRequestDto loginRequestDto = new LoginRequestDto("conas@gmail.com", DEFAULT_USER_PASSWORD);
        String authorizedUserUrl = getUrl(signUp(authorizedUserDto));
        signUp(unauthorizedUserDto);

        String sid = getLoginCookie(loginRequestDto);
        deleteUser(authorizedUserUrl, sid)
            .expectStatus().isForbidden();
    }

    @Test
    void find_by_id() {
        findUser(USER_URL + "/1")
            .expectStatus().isOk();
    }

    private WebTestClient.ResponseSpec assertRequestValidation(UserSaveRequestDto userSaveRequestDto) {
        return executePost(USER_URL)
            .body(Mono.just(userSaveRequestDto), UserSaveRequestDto.class)
            .exchange();
    }

    private String getUrl(EntityExchangeResult<byte[]> entityExchangeResult) {
        return entityExchangeResult
            .getResponseHeaders()
            .getLocation()
            .toASCIIString();
    }

    private WebTestClient.ResponseSpec findUser(String url) {
        return webTestClient
            .get()
            .uri(url)
            .exchange();
    }

    private WebTestClient.ResponseSpec updateUser(UserUpdateRequestDto userUpdateRequestDto, String uri, String sid) {
        return webTestClient.put()
            .uri(uri)
            .cookie(COOKIE_JSESSIONID, sid)
            .body(Mono.just(userUpdateRequestDto), UserUpdateRequestDto.class)
            .exchange();
    }

    private WebTestClient.ResponseSpec deleteUser(String url, String sid) {
        return webTestClient.delete()
            .uri(url)
            .cookie(COOKIE_JSESSIONID, sid)
            .exchange();
    }
}
