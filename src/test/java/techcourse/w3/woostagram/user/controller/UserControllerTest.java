package techcourse.w3.woostagram.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private static final String TEST_EMAIL = "test@test.com";
    private static final String TEST_PASSWORD = "Aa1234!!";


    @Autowired
    private WebTestClient webTestClient;

    @Test
    void showSignPage_showForm_isOk() {
        webTestClient.get().uri("/users/signup/form")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void create_correct_isFound() {
        webTestClient.post().uri("/users/signup")
                .body(fromFormData(EMAIL, TEST_EMAIL)
                        .with(PASSWORD, TEST_PASSWORD))
                .exchange()
                .expectStatus()
                .isFound();
    }
}