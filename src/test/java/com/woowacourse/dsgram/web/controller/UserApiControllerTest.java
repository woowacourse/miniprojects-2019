package com.woowacourse.dsgram.web.controller;


import com.woowacourse.dsgram.service.dto.user.AuthUserDto;
import com.woowacourse.dsgram.service.dto.user.SignUpUserDto;
import com.woowacourse.dsgram.service.dto.user.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest {
    private static int AUTO_INCREMENT = 0;

    @Autowired
    private WebTestClient webTestClient;

    private SignUpUserDto signUpUserDto;
    private AuthUserDto authUserDto;
    private String sessionCookie;
    private SignUpUserDto anotherUser;

    @BeforeEach
    void setUp() {
        signUpUserDto = SignUpUserDto.builder()
                .userName("김버디")
                .email(AUTO_INCREMENT + "buddy@buddy.com")
                .nickName(AUTO_INCREMENT + "buddy")
                .password("buddybuddy1!")
                .build();
        defaultSignUp(signUpUserDto, true)
                .expectStatus().isOk();
        authUserDto = new AuthUserDto(signUpUserDto.getEmail(), signUpUserDto.getPassword());
        sessionCookie = getCookie(authUserDto);

        anotherUser = SignUpUserDto.builder()
                .userName("김희CHORE")
                .email(AUTO_INCREMENT + "buddy@gmail.com")
                .nickName(AUTO_INCREMENT + "chore")
                .password("bodybuddy1!")
                .build();
    }

    private WebTestClient.ResponseSpec defaultSignUp(SignUpUserDto signUpUserDto, boolean willIncrease) {
        if (willIncrease) {
            AUTO_INCREMENT++;
        }

        return webTestClient.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(signUpUserDto), SignUpUserDto.class)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange();
    }

    @Test
    void signUp() {
        defaultSignUp(anotherUser, true)
                .expectStatus().isOk();
    }

    @Test
    void signUp_duplicatedEmail_thrown_exception() {
        SignUpUserDto anotherUser = SignUpUserDto.builder()
                .userName("서오상씨")
                .email(signUpUserDto.getEmail())
                .nickName("ooooohsang")
                .password("tjdhtkd12!")
                .build();

        defaultSignUp(anotherUser, false)
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("이미 사용중인 이메일입니다.");
    }

    @Test
    void signUp_duplicatedNickName_thrown_exception() {
        SignUpUserDto anotherUser = SignUpUserDto.builder()
                .userName("솔로스")
                .email("anotherEmail@naver.com")
                .nickName(signUpUserDto.getNickName())
                .password("ooollehh!")
                .build();

        defaultSignUp(anotherUser, false)
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("이미 사용중인 닉네임입니다.");
    }

    @Test
    void login() {
        getCookie(authUserDto);
    }

    private String getCookie(AuthUserDto authUserDto) {
        return webTestClient.post().uri("/api/users/login")
                .body(Mono.just(authUserDto), AuthUserDto.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }

    @Test
    void login_fail() {
        AuthUserDto authUserDto = new AuthUserDto("nonexistent", "nonexistent");
        webTestClient.post().uri("/api/users/login")
                .body(Mono.just(authUserDto), AuthUserDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Plz check your account.");
    }

    @Test
    void 회원정보_수정페이지_접근() {
        webTestClient.get().uri("/users/{userId}/edit", AUTO_INCREMENT)
                .header("Cookie", sessionCookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 회정정보_수정페이지_접근_비로그인() {
        webTestClient.get().uri("/users/{userId}/edit", AUTO_INCREMENT)
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("Location", ".*/login");
    }

    @Test
    void 회정정보_수정페이지_접근_다른_사용자() {
        defaultSignUp(anotherUser, true)
                .expectStatus().isOk();

        AuthUserDto authUserDto = new AuthUserDto(anotherUser.getEmail(), anotherUser.getPassword());
        webTestClient.get().uri("/users/{userId}/edit", AUTO_INCREMENT - 1)
                .header("Cookie", getCookie(authUserDto))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Plz check your account~");
    }

    @Test
    void 회원정보_수정() {
        UserDto updatedUserDto = UserDto.builder()
                .userName("김씨유")
                .intro("updatedIntro")
                .nickName("펠프스")
                .password("dsdsds")
                .webSite("updatedWebSite")
                .build();

        webTestClient.put().uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", sessionCookie)
                .body(Mono.just(updatedUserDto), UserDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 회원정보_수정_다른_사용자() {
        defaultSignUp(anotherUser, true)
                .expectStatus().isOk();

        UserDto updatedUserDto = UserDto.builder()
                .userName("김포비")
                .intro("updatedIntro")
                .nickName("반란군")
                .password("dsdsds")
                .webSite("updatedWebSite")
                .build();

        webTestClient.put().uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", sessionCookie)
                .body(Mono.just(updatedUserDto), UserDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Plz check your account~");
    }
}