package techcourse.w3.woostagram.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private static final String TEST_EMAIL = "test@test.com";
    private static final String TEST_EMAIL2 = "test2@test.com";
    private static final String TEST_EMAIL3 = "test3@test.com";
    private static final String TEST_EMAIL4 = "test4@test.com";
    private static final String TEST_PASSWORD = "Aa1234!!";
    private static final String JSESSIONID = "JSESSIONID";


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

    @Test
    void login_correct_isOk() {
        webTestClient.post().uri("/users/signup")
                .body(fromFormData(EMAIL, TEST_EMAIL2)
                        .with(PASSWORD, TEST_PASSWORD))
                .exchange()
                .expectStatus()
                .isFound();

        webTestClient.post().uri("/users/login")
                .body(fromFormData(EMAIL, TEST_EMAIL2)
                        .with(PASSWORD, TEST_PASSWORD))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void update_correct_isOk() {
        webTestClient.post().uri("/users/signup")
                .body(fromFormData(EMAIL, TEST_EMAIL3)
                        .with(PASSWORD, TEST_PASSWORD))
                .exchange()
                .expectStatus()
                .isFound();

        webTestClient.put().uri("/users")
                .cookie(JSESSIONID, getResponseCookie(TEST_EMAIL3, TEST_PASSWORD).getValue())
                .body(fromFormData("contents", "gggg")
                        .with("userName", "abc"))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void delete_userdata_isOk() {
        webTestClient.post().uri("/users/signup")
                .body(fromFormData(EMAIL, TEST_EMAIL4)
                        .with(PASSWORD, TEST_PASSWORD))
                .exchange()
                .expectStatus()
                .isFound();

        webTestClient.delete().uri("users")
                .cookie(JSESSIONID, getResponseCookie(TEST_EMAIL4, TEST_PASSWORD).getValue())
                .exchange()
                .expectStatus()
                .isOk();
    }

    protected ResponseCookie getResponseCookie(String email, String password) {
        return webTestClient.post().uri("/users/login")
                .body(fromFormData(EMAIL, email)
                        .with(PASSWORD, password))
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .returnResult(ResponseCookie.class)
                .getResponseCookies()
                .getFirst(JSESSIONID);
    }
}