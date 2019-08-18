package com.woowacourse.sunbook.presentation.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;


@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTemplate {

    @Autowired
    protected WebTestClient webTestClient;

    protected ResponseSpec request(HttpMethod method, String uri, Object object, HttpStatus httpStatus) {
        return webTestClient.method(method).uri(uri)
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
                .expectBody();
    }
}
