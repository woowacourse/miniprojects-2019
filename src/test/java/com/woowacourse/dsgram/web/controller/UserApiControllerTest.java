package com.woowacourse.dsgram.web.controller;


import com.woowacourse.dsgram.service.dto.user.AuthUserDto;
import com.woowacourse.dsgram.service.dto.user.SignUpUserDto;
import com.woowacourse.dsgram.service.dto.user.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

class UserApiControllerTest extends AbstractControllerTest {

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

    @Test
    void signUp_duplicatedEmail_thrown_exception() {
        SignUpUserDto anotherUser = SignUpUserDto.builder()
                .userName("서오상씨")
                .email(signUpUserDto.getEmail())
                .nickName("ooooohsang")
                .password("tjdhtkd12!")
                .build();

        checkExceptionMessage(defaultSignUp(anotherUser, false), "이미 사용중인 이메일입니다.");
    }

    @Test
    void signUp_duplicatedNickName_thrown_exception() {
        SignUpUserDto anotherUser = SignUpUserDto.builder()
                .userName("솔로스")
                .email("anotherEmail@naver.com")
                .nickName(signUpUserDto.getNickName())
                .password("ooollehh!")
                .build();

        checkExceptionMessage(defaultSignUp(anotherUser, false), "이미 사용중인 닉네임입니다.");
    }

    @Test
    void login() {
        getCookie(authUserDto);
    }

    @Test
    void login_fail() {
        AuthUserDto authUserDto = new AuthUserDto("nonexistent", "nonexistent");
        WebTestClient.ResponseSpec response = webTestClient.post().uri("/api/users/login")
                .body(Mono.just(authUserDto), AuthUserDto.class)
                .exchange();

        checkExceptionMessage(response, "회원정보가 일치하지 않습니다.");
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
        WebTestClient.ResponseSpec response = webTestClient.get().uri("/users/{userId}/edit", AUTO_INCREMENT - 1)
                .header("Cookie", getCookie(authUserDto))
                .exchange();

        checkExceptionMessage(response, "회원정보가 일치하지 않습니다.");
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
    void 회원정보_일부_수정_실패_닉네임_Null() {
        UserDto updatedUserDto = UserDto.builder()
                .userName("자손")
                .intro("")
                .nickName("")
                .password("dsdsds")
                .webSite("")
                .build();

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", sessionCookie)
                .body(Mono.just(updatedUserDto), UserDto.class)
                .exchange();

        checkExceptionMessage(response, "닉네임은 2~10자");
    }

    @Test
    void 회원정보_일부_수정_실패_패스워드_Null() {
        UserDto updatedUserDto = UserDto.builder()
                .userName("자손")
                .intro("")
                .nickName("jason")
                .password("")
                .webSite("")
                .build();

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", sessionCookie)
                .body(Mono.just(updatedUserDto), UserDto.class)
                .exchange();

        checkExceptionMessage(response, "비밀번호는 4~16자");
    }

    @Test
    void 회원정보_일부_수정_실패_이름_형식_불일치() {
        UserDto updatedUserDto = UserDto.builder()
                .userName("자")
                .intro("")
                .nickName("jason")
                .password("1234")
                .webSite("")
                .build();

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", sessionCookie)
                .body(Mono.just(updatedUserDto), UserDto.class)
                .exchange();

        checkExceptionMessage(response, "이름은 2~10자");
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

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", sessionCookie)
                .body(Mono.just(updatedUserDto), UserDto.class)
                .exchange();

        checkExceptionMessage(response, "회원정보가 일치하지 않습니다.");
    }

}