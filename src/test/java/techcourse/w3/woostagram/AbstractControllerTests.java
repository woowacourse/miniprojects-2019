package techcourse.w3.woostagram;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@TestPropertySource("classpath:application_test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractControllerTests {
    @Autowired
    private WebTestClient webTestClient;
    private String cookie;
    private static final String TEST_EMAIL = "a@naver.com";
    private static final String TEST_PW = "Aa1234!!";

    @BeforeEach
    protected void setUp() {
        loginRequest(TEST_EMAIL, TEST_PW);
    }

    protected EntityExchangeResult<byte[]> postMultipartRequest(String uri, MultiValueMap params) {
        return webTestClient.post().uri(uri)
                .header("Cookie", cookie)
                .syncBody(params)
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected <T> T getRequest(String uri, Class<T> bodyType) {
        return webTestClient.get()
                .uri(uri)
                .header("Cookie", cookie)
                .exchange()
                .expectBody(bodyType)
                .returnResult()
                .getResponseBody();
    }

    protected EntityExchangeResult<byte[]> getRequest(String uri) {
        return webTestClient.get()
                .uri(uri)
                .header("Cookie", cookie)
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected EntityExchangeResult<byte[]> postJsonRequest(String uri, Map<String, String> params) {
        return webTestClient.post()
                .uri(uri)
                .header("Cookie", cookie)
                .body(Mono.just(params), Map.class)
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected EntityExchangeResult<byte[]> postFormRequest(String uri, Map<String, String> params) {
        return webTestClient.post()
                .uri(uri)
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(mapBy(params))
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected EntityExchangeResult<byte[]> deleteRequest(String uri) {
        return webTestClient.delete()
                .uri(uri)
                .header("Cookie", cookie)
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected EntityExchangeResult<byte[]> putFormRequest(String uri, Map<String, String> params) {
        return webTestClient.put()
                .uri(uri)
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(mapBy(params))
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected EntityExchangeResult<byte[]> putJsonRequest(String uri, Map<String, String> params) {
        return webTestClient.put()
                .uri(uri)
                .header("Cookie", cookie)
                .body(Mono.just(params), Map.class)
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected void loginRequest(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        EntityExchangeResult<byte[]> result = postFormRequest("/users/login", params);
        this.cookie = result
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }

    private <T> BodyInserters.FormInserter<String> mapBy(Map<String, String> params) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData(Strings.EMPTY, Strings.EMPTY);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            body.with(entry.getKey(), entry.getValue());
        }
        return body;
    }

    protected void assertTest(String body, String... args) {
        for (String arg : args) {
            assertThat(body.contains(arg)).isTrue();
        }
    }

    protected void clearCookie() {
        cookie = "";
    }
}