package com.woowacourse.sunbook.presentation.template;

import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTemplate {

    @Autowired
    protected WebTestClient webTestClient;

    protected UserRequestDto userRequestDto = new UserRequestDto(
            new UserEmail("ddu0422@naver.com"),
            new UserName("mir", "lee"),
            new UserPassword("asdf1234!A")
    );

    protected UserRequestDto otherRequestDto = new UserRequestDto(
            new UserEmail("eara12sa@naver.com"),
            new UserName("abc", "lee"),
            new UserPassword("asdf1234!A")
    );

    protected UserRequestDto anotherRequestDto = new UserRequestDto(
            new UserEmail("andole@naver.com"),
            new UserName("abc", "lee"),
            new UserPassword("asdf1234!A")
    );

    protected ResponseSpec request(HttpMethod method, String uri, Object object, HttpStatus httpStatus) {
        return webTestClient.method(method).uri(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(object), Object.class)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus)
                ;
    }

    protected ResponseSpec loginAndRequest(HttpMethod method, String url, Object object, HttpStatus httpStatus, String sessionId) {
        return webTestClient.method(method).uri(url)
                .cookie("JSESSIONID", sessionId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(object), Object.class)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus)
                ;
    }

    protected BodyContentSpec respondApi(ResponseSpec responseSpec) {
        return responseSpec
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                ;
    }

    protected String loginSessionId(Object object) {
        return Objects.requireNonNull(webTestClient.method(HttpMethod.POST).uri("/api/signin")
                .body(Mono.just(object), Object.class)
                .exchange()
                .returnResult(String.class)
                .getResponseCookies()
                .getFirst("JSESSIONID"))
                .getValue()
                ;
    }
}
