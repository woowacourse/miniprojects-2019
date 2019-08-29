package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.dto.UserSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.woowacourse.edd.exceptions.DuplicateSubscriptionException.DUPLICATE_SUBSCRIPTION_MESSAGE;
import static com.woowacourse.edd.exceptions.UnauthenticatedException.UNAUTHENTICATED_MESSAGE;
import static com.woowacourse.edd.exceptions.UserNotFoundException.USER_NOT_FOUND_MESSAGE;
import static com.woowacourse.edd.presentation.controller.UserController.USER_URL;

public class SubscriptionControllerTests extends BasicControllerTests {

    @Test
    void subscribe() {
        UserSaveRequestDto subscriber = new UserSaveRequestDto("conas", "bonghee@gmail.com", "p@ssW0rd");
        UserSaveRequestDto subscribed = new UserSaveRequestDto("jm", "jayem@gmail.com", "p@ssW0rd");
        String url = signUp(subscribed).getResponseHeaders().getLocation().toASCIIString();
        signUp(subscriber);

        LoginRequestDto loginRequestDto = new LoginRequestDto("bonghee@gmail.com", "p@ssW0rd");
        String sid = getLoginCookie(loginRequestDto);

        executePost(url + "/subscribe")
            .cookie(COOKIE_JSESSIONID, sid)
            .exchange()
            .expectStatus().isCreated();
    }

    @Test
    @DisplayName("동일한 구독 요청 여러번")
    void double_subscription() {
        UserSaveRequestDto subscriber = new UserSaveRequestDto("conas", "babo@gmail.com", "p@ssW0rd");
        UserSaveRequestDto subscribed = new UserSaveRequestDto("jm", "chunjae@gmail.com", "p@ssW0rd");
        String url = signUp(subscribed).getResponseHeaders().getLocation().toASCIIString();
        signUp(subscriber);

        LoginRequestDto loginRequestDto = new LoginRequestDto("babo@gmail.com", "p@ssW0rd");
        String sid = getLoginCookie(loginRequestDto);

        subscribe(url, sid)
            .expectStatus().isCreated();

        assertFailBadRequest(subscribe(url, sid), DUPLICATE_SUBSCRIPTION_MESSAGE);
    }

    @Test
    void subscribe_fail_without_login() {
        UserSaveRequestDto subscribed = new UserSaveRequestDto("ethan", "ethan@gmail.com", "p@ssW0rd");
        String url = signUp(subscribed).getResponseHeaders().getLocation().toASCIIString();

        assertFailUnauthorized(webTestClient.post().uri(url + "/subscribe")
            .exchange(), UNAUTHENTICATED_MESSAGE);
    }

    @Test
    void find_subscribers() {
        UserSaveRequestDto subscriber1 = new UserSaveRequestDto("conas", "conas2@gmail.com", "p@ssW0rd");
        UserSaveRequestDto subscriber2 = new UserSaveRequestDto("heebong", "heebong@gmail.com", "p@ssW0rd");
        UserSaveRequestDto subscribed = new UserSaveRequestDto("jm", "jayem2@gmail.com", "p@ssW0rd");
        signUp(subscriber1);
        signUp(subscriber2);

        String url = signUp(subscribed).getResponseHeaders().getLocation().toASCIIString();

        LoginRequestDto loginRequestDto = new LoginRequestDto("conas2@gmail.com", "p@ssW0rd");
        String sid = getLoginCookie(loginRequestDto);

        subscribe(url, sid);

        loginRequestDto = new LoginRequestDto("heebong@gmail.com", "p@ssW0rd");
        sid = getLoginCookie(loginRequestDto);

        subscribe(url, sid);

        executeGet(url + "/count-subscribers")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.count").isEqualTo(2);
    }

    @DisplayName("구독자가 존재하지 않는데, 구독자 수를 조회하는 경우")
    @Test
    void find_subscribers_without_subscribed() {
        String url = USER_URL + "/" + Integer.MAX_VALUE + "/count-subscribers";
        assertFailNotFound(executeGet(url).exchange(), USER_NOT_FOUND_MESSAGE);
    }

    @Test
    void find_subscribeds() {
        UserSaveRequestDto subscriber1 = new UserSaveRequestDto("conas", "conas21@gmail.com", "p@ssW0rd");
        UserSaveRequestDto subscriber2 = new UserSaveRequestDto("heebong", "heebong1@gmail.com", "p@ssW0rd");

        String urlSubscriber1 = signUp(subscriber1).getResponseHeaders().getLocation().toASCIIString();
        String urlSubscriber2 = signUp(subscriber2).getResponseHeaders().getLocation().toASCIIString();

        String cookie = getDefaultLoginSessionId();

        subscribe(urlSubscriber1, cookie);
        subscribe(urlSubscriber2, cookie);

        findSubscriptions(DEFAULT_LOGIN_ID)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(2)
            .jsonPath("$[0].id").isNotEmpty()
            .jsonPath("$[0].name").isEqualTo("conas")
            .jsonPath("$[1].id").isNotEmpty()
            .jsonPath("$[1].name").isEqualTo("heebong");
    }

    @Test
    void cancel_subscription() {
        UserSaveRequestDto subscribed1 = new UserSaveRequestDto("conas", "conas211@gmail.com", "p@ssW0rd");
        UserSaveRequestDto subscribed2 = new UserSaveRequestDto("heebong", "heebong11@gmail.com", "p@ssW0rd");
        UserSaveRequestDto subscriber = new UserSaveRequestDto("jay", "jay@gmail.com", "p@ssW0rd");

        String urlSubscribed1 = signUp(subscribed1).getResponseHeaders().getLocation().toASCIIString();
        String urlSubscribed2 = signUp(subscribed2).getResponseHeaders().getLocation().toASCIIString();
        String urlSubscriber = signUp(subscriber).getResponseHeaders().getLocation().toASCIIString();
        String[] subscriberUrls = urlSubscriber.split("/");
        Long subscriberId = Long.valueOf(subscriberUrls[subscriberUrls.length - 1]);

        String cookie = getLoginCookie(new LoginRequestDto("jay@gmail.com", "p@ssW0rd"));

        subscribe(urlSubscribed1, cookie);
        subscribe(urlSubscribed2, cookie);

        findSubscriptions(subscriberId)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(2);

        executeDelete(urlSubscribed1 + "/subscribe")
            .cookie(COOKIE_JSESSIONID, cookie)
            .exchange()
            .expectStatus().isNoContent();

        findSubscriptions(subscriberId)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(1)
            .jsonPath("$[0].name").isEqualTo("heebong");
    }

    private WebTestClient.ResponseSpec subscribe(String url, String sid) {
        return executePost(url + "/subscribe")
            .cookie(COOKIE_JSESSIONID, sid)
            .exchange();
    }

    private WebTestClient.ResponseSpec findSubscriptions(Long subscriberId) {
        return executeGet(USER_URL + "/" + subscriberId + "/subscribed")
            .exchange();
    }
}
