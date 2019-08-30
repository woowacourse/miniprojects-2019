package com.wootecobook.turkey.user.controller;

import com.wootecobook.turkey.BaseControllerTests;
import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.user.service.dto.IntroductionRequest;
import com.wootecobook.turkey.user.service.dto.IntroductionResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.wootecobook.turkey.user.service.IntroductionService.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntroductionApiControllerTests extends BaseControllerTests {

    private static final String INTRODUCTION_URI = "/api/users/{userId}/introduction";
    private static final String HOMETOWN = "HOMETOWN";
    private static final String CURRENT_CITY = "CURRENT_CITY";
    private static final String EDUCATION = "EDUCATION";
    private static final String COMPANY = "COMPANY";

    @Autowired
    private WebTestClient webTestClient;

    private Long userId;

    @BeforeEach
    void setUp() {
        userId = addUser(VALID_USER_NAME, VALID_USER_EMAIL, VALID_USER_PASSWORD);
    }

    @Test
    void 소개_조회_초기값() {
        // when
        IntroductionResponse response = webTestClient.get()
                .uri(INTRODUCTION_URI, userId)
                .cookie(JSESSIONID, logIn(VALID_USER_EMAIL, VALID_USER_PASSWORD))
                .exchange()
                .expectBody(IntroductionResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getCompany()).isNull();
        assertThat(response.getHometown()).isNull();
        assertThat(response.getEducation()).isNull();
        assertThat(response.getCurrentCity()).isNull();
    }

    @Test
    void 없는_유저_소개_조회_에러() {
        // when
        ErrorMessage errorMessage = webTestClient.get()
                .uri(INTRODUCTION_URI, Long.MAX_VALUE)
                .cookie(JSESSIONID, logIn(VALID_USER_EMAIL, VALID_USER_PASSWORD))
                .exchange()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(errorMessage.getMessage()).isEqualTo(NOT_FOUND_INTRODUCTION_MESSAGE);
    }

    @Test
    void 소개_수정() {
        // given
        IntroductionRequest introductionRequest = IntroductionRequest.builder()
                .hometown(HOMETOWN)
                .education(EDUCATION)
                .currentCity(CURRENT_CITY)
                .company(COMPANY)
                .userId(userId)
                .build();

        // when
        IntroductionResponse response = webTestClient.put()
                .uri(INTRODUCTION_URI, userId)
                .cookie(JSESSIONID, logIn(VALID_USER_EMAIL, VALID_USER_PASSWORD))
                .body(Mono.just(introductionRequest), IntroductionRequest.class)
                .exchange()
                .expectBody(IntroductionResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getCompany()).isEqualTo(COMPANY);
        assertThat(response.getHometown()).isEqualTo(HOMETOWN);
        assertThat(response.getEducation()).isEqualTo(EDUCATION);
        assertThat(response.getCurrentCity()).isEqualTo(CURRENT_CITY);
    }

    @Test
    void 타인이_소개_수정_에러() {
        // given
        Long anotherUserId = addUser(VALID_USER_NAME, VALID_USER_EMAIL + "a", VALID_USER_PASSWORD);
        IntroductionRequest introductionRequest = IntroductionRequest.builder()
                .hometown(HOMETOWN)
                .education(EDUCATION)
                .currentCity(CURRENT_CITY)
                .company(COMPANY)
                .userId(userId)
                .build();

        // when
        ErrorMessage errorMessage = webTestClient.put()
                .uri(INTRODUCTION_URI, userId)
                .cookie(JSESSIONID, logIn(VALID_USER_EMAIL + "a", VALID_USER_PASSWORD))
                .body(Mono.just(introductionRequest), IntroductionRequest.class)
                .exchange()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(errorMessage.getMessage()).isEqualTo(MISMATCH_USER_MESSAGE);

        deleteUser(anotherUserId, VALID_USER_EMAIL + "a", VALID_USER_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        deleteUser(userId, VALID_USER_EMAIL, VALID_USER_PASSWORD);
    }
}