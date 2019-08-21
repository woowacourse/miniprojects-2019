package com.woowacourse.dsgram.web.controller;


import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import com.woowacourse.dsgram.service.dto.user.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

class UserApiControllerTest extends AbstractControllerTest {

    private AuthUserRequest authUserRequest;
    private String cookie;
    private SignUpUserRequest anotherUser;
    private SignUpUserRequest signUpUserRequest = SignUpUserRequest.builder()
            .email("success@gmail.com")
            .userName("success")
            .nickName("success")
            .password("1234")
            .build();

    @BeforeEach
    void setUp() {
        signUpUserRequest = SignUpUserRequest.builder()
                .email(AUTO_INCREMENT + "success@gmail.com")
                .userName("success")
                .nickName(AUTO_INCREMENT + "success")
                .password("1234")
                .build();
        getResponseAfterSignUp(signUpUserRequest);

        cookie = getCookieAfterSignUpAndLogin();
    }

    @Test
    void signUp_duplicatedEmail_thrown_exception() {
        SignUpUserRequest anotherUser = SignUpUserRequest.builder()
                .userName("서오상씨")
                .email(signUpUserRequest.getEmail())
                .nickName("ooooohsang")
                .password("tjdhtkd12!")
                .build();

        checkExceptionMessage(getResponseAfterSignUp(anotherUser), "이미 사용중인 이메일입니다.");
    }

    @Test
    void signUp_blankEmail_thrown_exception() {
        SignUpUserRequest anotherUser = SignUpUserRequest.builder()
                .userName("서오상씨")
                .email("")
                .nickName("os94")
                .password("tjdhtkd12!")
                .build();

        checkExceptionMessage(getResponseAfterSignUp(anotherUser), "이메일 양식");
    }

    @Test
    void signUp_InvalidEmail_thrown_exception() {
        SignUpUserRequest anotherUser = SignUpUserRequest.builder()
                .userName("서오상씨")
                .email("@@")
                .nickName("os94")
                .password("tjdhtkd12!")
                .build();

        checkExceptionMessage(getResponseAfterSignUp(anotherUser), "이메일 양식");
    }

    @Test
    void signUp_duplicatedNickName_thrown_exception() {
        SignUpUserRequest anotherUser = SignUpUserRequest.builder()
                .userName("솔로스")
                .email("anotherEmail@naver.com")
                .nickName(signUpUserRequest.getNickName())
                .password("ooollehh!")
                .build();

        checkExceptionMessage(getResponseAfterSignUp(anotherUser), "이미 사용중인 닉네임입니다.");
    }

    @Test
    void login() {
        getCookieAfterSignUpAndLogin();
    }

    @Test
    void login_fail() {
        AuthUserRequest authUserRequest = new AuthUserRequest("nonexistent", "nonexistent");
        ResponseSpec response = webTestClient.post().uri("/api/users/login")
                .body(Mono.just(authUserRequest), AuthUserRequest.class)
                .exchange();

        checkExceptionMessage(response, "회원정보가 일치하지 않습니다.");
    }

    @Test
    void 회원정보_수정페이지_접근() {
        cookie = getCookieAfterSignUpAndLogin();
        webTestClient.get().uri("/users/{userId}/edit", AUTO_INCREMENT)
                .header("Cookie", cookie)
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
        ResponseSpec response = webTestClient.get().uri("/users/{userId}/edit", AUTO_INCREMENT)
                .header("Cookie", getCookieAfterSignUpAndLogin())
                .exchange();

        checkExceptionMessage(response, "회원정보가 일치하지 않습니다.");
    }

    // TODO: 2019-08-21

    @Test
    void 회원정보_수정() {
        MultipartBodyBuilder multipartBodyBuilder =
                createMultipartBodyBuilder("포비", "intro", "포비", "dsdsds", "");

        webTestClient.put()
                .uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", cookie)
                .body(BodyInserters.fromObject(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isOk();


    }

    @Test
    void 회원정보_일부_수정_실패_닉네임_Null() {

        MultipartBodyBuilder multipartBodyBuilder =
                createMultipartBodyBuilder("포비", "", "", "dsdsds", "");

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", cookie)
                .body(BodyInserters.fromObject(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isBadRequest();

        checkExceptionMessage(response, "닉네임은 2~10자");
    }

    @Test
    void 회원정보_일부_수정_실패_패스워드_Null() {

        MultipartBodyBuilder multipartBodyBuilder =
                createMultipartBodyBuilder("자손", "", "jason", "", "");

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", cookie)
                .body(BodyInserters.fromObject(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isBadRequest();

        checkExceptionMessage(response, "비밀번호는 4~16자");
    }

    @Test
    void 회원정보_일부_수정_실패_이름_형식_불일치() {
        MultipartBodyBuilder multipartBodyBuilder =
                createMultipartBodyBuilder("자", "", "jason", "1234", "");

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", cookie)
                .body(BodyInserters.fromObject(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isBadRequest();

        checkExceptionMessage(response, "이름은 2~10자");
    }

    @Test
    void 회원정보_수정_다른_사용자() {
        // TODO: 2019-08-21
        getResponseAfterSignUp(anotherUser)
                .expectStatus().isOk();

        UserDto updatedUserDto = UserDto.builder()
                .userName("김포비")
                .intro("updatedIntro")
                .nickName("반란군")
                .password("dsdsds")
                .webSite("updatedWebSite")
                .build();

        ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", AUTO_INCREMENT)
                .header("Cookie", cookie)
                .body(Mono.just(updatedUserDto), UserDto.class)
                .exchange();

        checkExceptionMessage(response, "회원정보가 일치하지 않습니다.");
    }

    @Test
    void user_다른_사용자가_탈퇴_시도() {
//        SignUpUserRequest anotherUser = SignUpUserRequest.builder()
//                .userName("김희CHORE")
//                .email(AUTO_INCREMENT + "buddy@gmail.com")
//                .nickName(AUTO_INCREMENT + "chore")
//                .password("bodybuddy1!")
//                .build();
//        getResponseAfterSignUp(anotherUser, true);
//        String anotherUserCookie = loginAndGetCookie(new AuthUserRequest(anotherUser.getEmail(), anotherUser.getPassword()));

        String anotherUserCookie = getCookieAfterSignUpAndLogin();
        webTestClient.delete().uri("/api/users/{userId}", AUTO_INCREMENT - 1)
                .header("Cookie", anotherUserCookie)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void user_탈퇴() {
        long[] userId = new long[1];
        long[] articleId = new long[1];

        String cookie = getCookieAfterSignUpAndLogin();
        String anotherCookie = getCookieAfterSignUpAndLogin();

        requestWithBodyBuilder(createMultipartBodyBuilder(), HttpMethod.POST, "/api/articles", cookie)
                .expectBody()
                .jsonPath("$.id")
                .value(id -> articleId[0] = Long.parseLong(id.toString()))
                .jsonPath("$.author.id")
                .value(id -> userId[0] = Long.parseLong(id.toString()));

        webTestClient.delete().uri("/api/users/{userId}", userId[0])
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
        webTestClient.get().uri("/articles/{articleId}", articleId[0])
                .header("Cookie", anotherCookie)
                .exchange()
                .expectStatus().isBadRequest();
    }



    private MultipartBodyBuilder createMultipartBodyBuilder(String userName,
                                                            String intro, String nickName,
                                                            String password, String website
    ) {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", new ByteArrayResource(new byte[]{1, 2, 3, 4}) {
            @Override
            public String getFilename() {
                return "catImage.jpeg";
            }
        }, MediaType.IMAGE_JPEG);
        bodyBuilder.part("userName", userName);
        bodyBuilder.part("nickName", nickName);
        bodyBuilder.part("intro", intro);
        bodyBuilder.part("webSite", website);
        bodyBuilder.part("password", password);
        return bodyBuilder;
    }
}