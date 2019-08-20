package com.woowacourse.zzazanstagram.model.follow.controller;

import com.woowacourse.zzazanstagram.model.RequestTemplate;
import com.woowacourse.zzazanstagram.model.support.WebTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FollowControllerTest extends RequestTemplate {

    @BeforeEach
    void setUpFollowController() {
        saveOtherMember("2ndMember", "test2@gmail.com");
        saveOtherMember("3rdMember", "test3@gmail.com");
        saveOtherMember("4thMember", "test4@gmail.com");
    }

    @Test
    void 팔로워_구하기() {
        // 1번 유저를 2번과, 3번이 팔로우한다
        postHeaderWithLogin("/follow")
                .body(WebTestHelper.followForm(2L, 1L))
                .exchange()
                .expectStatus().isOk();

        postHeaderWithLogin("/follow")
                .body(WebTestHelper.followForm(3L, 1L))
                .exchange()
                .expectStatus().isOk();

        List responseBody = getHeaderWithLogin("/follow/follower/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody.size()).isEqualTo(2);
    }

    @Test
    void 팔로잉_구하기() {
        // 1번 유저가 2, 3, 4 번 유저를 팔로우 한다
        postHeaderWithLogin("/follow")
                .body(WebTestHelper.followForm(1L, 2L))
                .exchange()
                .expectStatus().isOk();

        postHeaderWithLogin("/follow")
                .body(WebTestHelper.followForm(1L, 3L))
                .exchange()
                .expectStatus().isOk();

        postHeaderWithLogin("/follow")
                .body(WebTestHelper.followForm(1L, 4L))
                .exchange()
                .expectStatus().isOk();

        List responseBody = getHeaderWithLogin("/follow/following/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody.size()).isEqualTo(3);
    }
}
