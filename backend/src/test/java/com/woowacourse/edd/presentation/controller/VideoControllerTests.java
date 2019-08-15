package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.EddApplicationTests;
import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VideoControllerTests extends EddApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void saveVideoTest() {
        String youtubeId = "1234!@#$asdf";
        String title = "제목";
        String contents = "내용";
        String date = getFormedDate();

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(youtubeId, title, contents);

        webTestClient.post().uri("/api/v1/videos")
                .body(Mono.just(videoSaveRequestDto), VideoSaveRequestDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.youtubeId").isEqualTo(youtubeId)
                .jsonPath("$.title").isEqualTo(title)
                .jsonPath("$.contents").isEqualTo(contents)
                .jsonPath("$.createDate").isEqualTo(date);
    }

    @Test
    void saveVideoTestWhenYoutubeIdError() {
        String youtubeId = null;
        String title = "제목";
        String contents = "내용";
        String date = getFormedDate();

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(youtubeId, title, contents);

        webTestClient.post().uri("/api/v1/videos")
                .body(Mono.just(videoSaveRequestDto), VideoSaveRequestDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.result").isNotEmpty()
                .jsonPath("$.result").isEqualTo("FAIL")
                .jsonPath("$.message").isNotEmpty();
    }

    @Test
    void saveVideoTestWhenTitleError() {
        String youtubeId = "1234!@#$asdf";
        String title = "";
        String contents = "내용";
        String date = getFormedDate();

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(youtubeId, title, contents);

        webTestClient.post().uri("/api/v1/videos")
                .body(Mono.just(videoSaveRequestDto), VideoSaveRequestDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.result").isNotEmpty()
                .jsonPath("$.result").isEqualTo("FAIL")
                .jsonPath("$.message").isNotEmpty();

    }

    @Test
    void saveVideoTestWhenContentsError() {
        String youtubeId = "1234!@#$asdf";
        String title = "aaa";
        String contents = " ";
        String date = getFormedDate();

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(youtubeId, title, contents);

        webTestClient.post().uri("/api/v1/videos")
                .body(Mono.just(videoSaveRequestDto), VideoSaveRequestDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.result").isNotEmpty()
                .jsonPath("$.result").isEqualTo("FAIL")
                .jsonPath("$.message").isNotEmpty();
    }

    @Test
    void find_Videos_By_Date_Test() {
        webTestClient.get().uri("/api/v1/videos?filter=date&page=0&limit=5")
                .exchange()
                .expectStatus().isOk();
    }

    private String getFormedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        return now.format(formatter);
    }
}
