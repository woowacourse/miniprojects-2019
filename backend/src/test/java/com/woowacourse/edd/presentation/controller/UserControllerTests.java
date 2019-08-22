package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.dto.UserRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.StatusAssertions;
import reactor.core.publisher.Mono;

import static com.woowacourse.edd.application.dto.UserRequestDto.INVALID_EMAIL_MESSAGE;
import static com.woowacourse.edd.application.dto.UserRequestDto.INVALID_NAME_MESSAGE;
import static com.woowacourse.edd.application.dto.UserRequestDto.INVALID_PASSWORD_MESSAGE;
import static com.woowacourse.edd.presentation.controller.UserController.USER_URL;

public class UserControllerTests extends BasicControllerTests {

    @Test
    void email_validation() {
        UserRequestDto userRequestDto = new UserRequestDto("robby", "asdadasdasd", "P@ssW0rd");
        assertFailBadRequest(assertRequestValidation(userRequestDto), INVALID_EMAIL_MESSAGE);
    }

    @Test
    void name_validation() {
        UserRequestDto userRequestDto = new UserRequestDto("r", "als5610@naver.com", "P@ssW0rd");
        assertFailBadRequest(assertRequestValidation(userRequestDto), INVALID_NAME_MESSAGE);
    }

    @Test
    void password_validation() {
        UserRequestDto userRequestDto = new UserRequestDto("robby", "als5610@naver.com", "Passw0rd");
        assertFailBadRequest(assertRequestValidation(userRequestDto), INVALID_PASSWORD_MESSAGE);
    }

    @Test
    void user_save() {
        UserRequestDto userSaveRequestDto = new UserRequestDto("robby", "shit@email.com", "P@ssW0rd");

        findUser(getUrl(signUp(userSaveRequestDto)))
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo("robby")
            .jsonPath("$.email").isEqualTo("shit@email.com");
    }

    @Test
    void user_update() {
        UserRequestDto userSaveRequestDto = new UserRequestDto("robby", "shit123@email.com", "P@ssW0rd");
        LoginRequestDto loginRequestDto = new LoginRequestDto("shit123@email.com", "P@ssW0rd");

        String url = getUrl(signUp(userSaveRequestDto));
        String sid = getLoginCookie(loginRequestDto);
        UserRequestDto userRequestDto = new UserRequestDto("jm", "hansome@gmail.com", "P!ssW0rd");

        updateUser(userRequestDto, url, sid)
            .isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo(userRequestDto.getName())
            .jsonPath("$.email").isEqualTo(userRequestDto.getEmail());
    }

    @Test
    @DisplayName("가입된 유저가 다른 유저의 수정을 시도할 때")
    void unauthorized_user_update() {
        UserRequestDto authorizedUser = new UserRequestDto("Jmm", "jm@naver.com", "p@ssW0rd");
        UserRequestDto unauthorizedUser = new UserRequestDto("conas", "conas@naver.com", "p@ssW0rd");
        UserRequestDto unauthorizedUpdateRequest = new UserRequestDto("pobi", "conas@naver.com", "p@ssW0rd");

        String url = getUrl(signUp(authorizedUser));
        signUp(unauthorizedUser);
        LoginRequestDto loginRequestDto = new LoginRequestDto("conas@naver.com", "p@ssW0rd");
        String sid = getLoginCookie(loginRequestDto);

        updateUser(unauthorizedUpdateRequest, url, sid).isForbidden();
    }

    @Test
    @DisplayName("가입되지 않은 유저가 가입되지 않은 유저 삭제를 시도할 때")
    void no_signin_delete_no_signin_user() {
        deleteUser(USER_URL + "/999", null)
            .isUnauthorized();
    }

    @Test
    @DisplayName("가입되지 않은 유저가 가입된 유저 삭제를 시도할 때")
    void no_sigin_delete_user() {
        deleteUser(USER_URL + "/1", null)
            .isUnauthorized();
    }

    @Test
    @DisplayName("가입된 유저가 자신의 유저 삭제를 시도할 때")
    void authorized_user_delete_no_content() {
        UserRequestDto userRequestDto = new UserRequestDto("robby", "shit222@email.com", "P@ssW0rd");
        LoginRequestDto loginRequestDto = new LoginRequestDto("shit222@email.com", "P@ssW0rd");
        String url = getUrl(signUp(userRequestDto));
        String sid = getLoginCookie(loginRequestDto);
        deleteUser(url, sid)
            .isNoContent();
    }

    @Test
    @DisplayName("가입된 유저가 다른 유저의 삭제를 시도할 때")
    void unauthorized_user_delete() {
        UserRequestDto authorizedUserDto = new UserRequestDto("normal", "normalUser@gmail.com", "p@ssW0rd");
        UserRequestDto unauthorizedUserDto = new UserRequestDto("conas", "conas@gmail.com", "p@ssW0rd");
        LoginRequestDto loginRequestDto = new LoginRequestDto("conas@gmail.com", "p@ssW0rd");
        String authorizedUserUrl = getUrl(signUp(authorizedUserDto));
        signUp(unauthorizedUserDto);

        String sid = getLoginCookie(loginRequestDto);
        deleteUser(authorizedUserUrl, sid)
            .isForbidden();
    }

    @Test
    @DisplayName("가입된 유저가 탈퇴한 유저의 정보를 조회할 때")
    void user_() {
    }

    @Test
    void find_by_id() {
        findUser(USER_URL + "/1").isOk();
    }

    private StatusAssertions assertRequestValidation(UserRequestDto userRequestDto) {
        return executePost(USER_URL)
            .body(Mono.just(userRequestDto), UserRequestDto.class)
            .exchange()
            .expectStatus();
    }

    private String getUrl(EntityExchangeResult<byte[]> entityExchangeResult) {
        return entityExchangeResult
            .getResponseHeaders()
            .getLocation()
            .toASCIIString();
    }

    private StatusAssertions findUser(String url) {
        return webTestClient
            .get()
            .uri(url)
            .exchange()
            .expectStatus();
    }

    private StatusAssertions updateUser(UserRequestDto userRequestDto, String uri, String sid) {
        return webTestClient.put()
            .uri(uri)
            .cookie(COOKIE_JSESSIONID, sid)
            .body(Mono.just(userRequestDto), UserRequestDto.class)
            .exchange()
            .expectStatus();
    }

    private StatusAssertions deleteUser(String url, String sid) {
        return webTestClient.delete()
            .uri(url)
            .cookie(COOKIE_JSESSIONID, sid)
            .exchange()
            .expectStatus();
    }
}
