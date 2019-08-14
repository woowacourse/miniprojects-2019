package com.woowacourse.edd.controller;

import com.woowacourse.edd.EddApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;


public class VideoControllerTests extends EddApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void find_Videos_By_Date_Test() {
        webTestClient.get().uri("/api/v1/videos?filter=date&page=0&limit=5")
                .exchange()
                .expectStatus().isOk();
    }
}
