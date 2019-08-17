package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import com.woowacourse.sunbook.presentation.template.TestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

class LoginControllerTest extends TestTemplate {
    private static final String USER_EMAIL = "ddu0422@naver.com";
    private static final String NEW_USER_EMAIL = "ddu0422@gmail.com";
    private static final String USER_NAME = "mir";
    private static final String USER_PASSWORD = "asdf1234!A";
    private static final String WRONG_USER_PASSWORD = "ASDF1324!a";

    private UserEmail userEmail = new UserEmail(USER_EMAIL);
    private UserEmail newUserEmail = new UserEmail(NEW_USER_EMAIL);
    private UserName userName = new UserName(USER_NAME);
    private UserPassword userPassword = new UserPassword(USER_PASSWORD);
    private UserPassword wrongUserPassword = new UserPassword(WRONG_USER_PASSWORD);
    private UserRequestDto userLoginRequestDto = new UserRequestDto(userEmail, userName, userPassword);
    private UserRequestDto userWrongRequestDto = new UserRequestDto(userEmail, userName, wrongUserPassword);
    private UserRequestDto userSignInRequestDto = new UserRequestDto(newUserEmail, userName, userPassword);

    @Test
    void 로그인_성공() {
        webTestClient.post().uri("/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userLoginRequestDto), UserRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$..email").isEqualTo(USER_EMAIL)
                .jsonPath("$..name").isEqualTo(USER_NAME)
                ;
    }

    @Test
    void 로그인_실패() {
        webTestClient.post().uri("/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userWrongRequestDto), UserRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.errorMessage").isEqualTo("로그인 실패")
                ;
    }

    @Test
    void 회원가입_성공() {
        webTestClient.post().uri("/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userSignInRequestDto), UserRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$..email").isEqualTo(NEW_USER_EMAIL)
                .jsonPath("$..name").isEqualTo(USER_NAME)
                ;
    }

    @Test
    void 중복된_이메일로_인한_회원가입_실패() {
        webTestClient.post().uri("/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userLoginRequestDto), UserRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.errorMessage").isEqualTo("중복된 이메일입니다.")
                ;
    }
}
