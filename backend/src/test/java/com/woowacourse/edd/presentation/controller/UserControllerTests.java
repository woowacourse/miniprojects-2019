package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.dto.UserSaveRequestDto;
import com.woowacourse.edd.application.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_EMAIL_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_NAME_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_PASSWORD_MESSAGE;
import static com.woowacourse.edd.presentation.controller.UserController.USER_URL;

public class UserControllerTests extends BasicControllerTests {

    @Test
    void email_validation() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("robby", "asdadasdasd", "P@ssW0rd");
        assertFailBadRequest(assertRequestValidation(userSaveRequestDto), INVALID_EMAIL_MESSAGE);
    }

    @Test
    void name_validation() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("r", "als5610@naver.com", "P@ssW0rd");
        assertFailBadRequest(assertRequestValidation(userSaveRequestDto), INVALID_NAME_MESSAGE);
    }

    @Test
    void password_validation() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("robby", "als5610@naver.com", "Passw0rd");
        assertFailBadRequest(assertRequestValidation(userSaveRequestDto), INVALID_PASSWORD_MESSAGE);
    }

    @Test
    void user_save() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("robby", "shit@email.com", "P@ssW0rd");

        findUser(getUrl(signUp(userSaveRequestDto)))
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("robby")
            .jsonPath("$.email").isEqualTo("shit@email.com");
    }

    @Test
    void user_update() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("robby", "shit123@email.com", "P@ssW0rd");
        LoginRequestDto loginRequestDto = new LoginRequestDto("shit123@email.com", "P@ssW0rd");

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
    @DisplayName("가입된 유저가 다른 유저의 수정을 시도할 때")
    void unauthorized_user_update() {
        UserSaveRequestDto authorizedUser = new UserSaveRequestDto("Jmm", "jm@naver.com", "p@ssW0rd");
        UserSaveRequestDto unauthorizedUser = new UserSaveRequestDto("conas", "conas@naver.com", "p@ssW0rd");
        UserUpdateRequestDto unauthorizedUpdateRequest = new UserUpdateRequestDto("pobi", "conas@naver.com");

        String url = getUrl(signUp(authorizedUser));
        signUp(unauthorizedUser);
        LoginRequestDto loginRequestDto = new LoginRequestDto("conas@naver.com", "p@ssW0rd");
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
    @DisplayName("가입된 유저가 자신의 유저 삭제를 시도할 때")
    void authorized_user_delete_no_content() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("robby", "shit222@email.com", "P@ssW0rd");
        LoginRequestDto loginRequestDto = new LoginRequestDto("shit222@email.com", "P@ssW0rd");
        String url = getUrl(signUp(userSaveRequestDto));
        String sid = getLoginCookie(loginRequestDto);
        deleteUser(url, sid)
            .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("가입된 유저가 다른 유저의 삭제를 시도할 때")
    void unauthorized_user_delete() {
        UserSaveRequestDto authorizedUserDto = new UserSaveRequestDto("normal", "normalUser@gmail.com", "p@ssW0rd");
        UserSaveRequestDto unauthorizedUserDto = new UserSaveRequestDto("conas", "conas@gmail.com", "p@ssW0rd");
        LoginRequestDto loginRequestDto = new LoginRequestDto("conas@gmail.com", "p@ssW0rd");
        String authorizedUserUrl = getUrl(signUp(authorizedUserDto));
        signUp(unauthorizedUserDto);

        String sid = getLoginCookie(loginRequestDto);
        deleteUser(authorizedUserUrl, sid)
            .expectStatus().isForbidden();
    }

    @Test
    @DisplayName("가입된 유저가 탈퇴한 유저의 정보를 조회할 때")
    void user_() {
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
