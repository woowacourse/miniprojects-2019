package techcourse.w3.woostagram;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import techcourse.w3.woostagram.user.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
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

    protected EntityExchangeResult<byte[]> postMultipartRequest(String uri, Class mappingClass, MultipartBodyBuilder bodyBuilder, String... args) {
        return webTestClient.post().uri(uri)
                .header("Cookie", cookie)
                .syncBody(mapMultipart(bodyBuilder, mappingClass, args))
                .exchange()
                .expectBody()
                .returnResult();
    }

    private MultiValueMap mapMultipart(MultipartBodyBuilder bodyBuilder, Class mappingClass, String[] args) {
        for (int i = 1; i < mappingClass.getDeclaredFields().length; i++) {
            bodyBuilder.part(mappingClass.getDeclaredFields()[i].getName(), args[i]);
        }
        return bodyBuilder.build();
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

    protected EntityExchangeResult<byte[]> postJsonRequest(String uri, Class mappingClass, String... args) {
        Map<String, String> params = new HashMap();

        for (int i = 0; i < mappingClass.getDeclaredFields().length; i++) {
            params.put(mappingClass.getDeclaredFields()[i].getName(), args[i]);
        }

        return webTestClient.post()
                .uri(uri)
                .header("Cookie", cookie)
                .body(Mono.just(params), Map.class)
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected EntityExchangeResult<byte[]> postFormRequest(String uri, Class mappingClass, String... args) {
        return webTestClient.post()
                .uri(uri)
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(mapBy(mappingClass, args))
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

    protected EntityExchangeResult<byte[]> putFormRequest(String uri, Class mappingClass, String... args) {
        return webTestClient.put()
                .uri(uri)
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(mapBy(mappingClass, args))
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected EntityExchangeResult<byte[]> putJsonRequest(String uri, Class mappingClass, String... args) {
        Map<String, String> params = new HashMap();

        for (int i = 0; i < mappingClass.getDeclaredFields().length; i++) {
            params.put(mappingClass.getDeclaredFields()[i].getName(), args[i]);
        }

        return webTestClient.put()
                .uri(uri)
                .header("Cookie", cookie)
                .body(Mono.just(params), Map.class)
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected void loginRequest(String email, String password) {
        EntityExchangeResult<byte[]> result = postFormRequest("/users/login", UserDto.class,email, password);
        this.cookie = result
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }

    private <T> BodyInserters.FormInserter<String> mapBy(Class<T> classType, String... parameters) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData(Strings.EMPTY, Strings.EMPTY);

        for (int i = 0; i < classType.getDeclaredFields().length; i++) {
            body.with(classType.getDeclaredFields()[i].getName(), parameters[i]);
        }
        return body;
    }

    protected void assertTest(String body, String... args) {
        for (String arg : args) {
            assertThat(body.contains(arg)).isTrue();
        }
    }

    @AfterEach
    protected void tearDown() {

    }
}