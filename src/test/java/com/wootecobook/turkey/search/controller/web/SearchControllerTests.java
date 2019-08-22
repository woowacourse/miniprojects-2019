package com.wootecobook.turkey.search.controller.web;

import com.wootecobook.turkey.commons.BaseControllerTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchControllerTests extends BaseControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    private final String uri = linkTo(SearchController.class).toString();

    @Test
    void 검색_요청() {
        String email = "deleteerror@delete.del";
        String name = "delete";

        addUser(name, email, VALID_USER_PASSWORD);

        // when & then
        webTestClient.get().uri(uri + "/dpu")
                .cookie(JSESSIONID, logIn(email, VALID_USER_PASSWORD))
                .exchange()
                .expectStatus().isOk();
    }
}