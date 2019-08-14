package com.woowacourse.dsgram.web.controller;


import com.woowacourse.dsgram.service.dto.SignUpUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest {
    private static int FLAG_NO = 0;

    @Autowired
    private WebTestClient webTestClient;

    private SignUpUserDto signUpUserDto;

    @BeforeEach
    void setUp() {
        FLAG_NO++;

        signUpUserDto = SignUpUserDto.builder()
                .userName("김버디")
                .email(FLAG_NO + "buddy@buddy.com")
                .nickName(FLAG_NO + "buddy")
                .password("buddybuddy1!")
                .build();

        defaultSignUp(signUpUserDto)
                .expectStatus().isCreated();

    }

    private WebTestClient.ResponseSpec defaultSignUp(SignUpUserDto signUpUserDto) {
        return webTestClient.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(signUpUserDto), SignUpUserDto.class)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange();
    }

    @Test
    void signUp() {
        SignUpUserDto anotherUser = SignUpUserDto.builder()
                .userName("버디킴")
                .email("buddy@gmail.com")
                .nickName("body")
                .password("bodybuddy1!")
                .build();

        defaultSignUp(anotherUser)
                .expectStatus().isCreated();
    }

    @Test
    void signUp_duplicatedEmail_thrown_exception() {
        SignUpUserDto anotherUser = SignUpUserDto.builder()
                .userName("서오상씨")
                .email(signUpUserDto.getEmail())
                .nickName("ooooohsang")
                .password("tjdhtkd12!")
                .build();

        // TODO: 2019-08-14 Process Exception!!
        defaultSignUp(anotherUser)
                .expectStatus().is5xxServerError();
    }

    @Test
    void signUp_duplicatedNickName_thrown_exception() {
        SignUpUserDto anotherUser = SignUpUserDto.builder()
                .userName("솔로스")
                .email("anotherEmail@naver.com")
                .nickName(signUpUserDto.getNickName())
                .password("ooollehh!")
                .build();

        // TODO: 2019-08-14 Process Exception!!
        defaultSignUp(anotherUser)
                .expectStatus().is5xxServerError();
    }
}