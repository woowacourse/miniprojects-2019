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

    private final String DEFAULT_VIDEO_YOUTUBEID = "S8e1geEpnTA";
    private final String DEFAULT_VIDEO_TITLE = "제목";
    private final String DEFAULT_VIDEO_CONTENTS = "내용";

    @Test
    void saveVideoTest() {
        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        webTestClient.post().uri("/v1/videos")
                .body(Mono.just(videoSaveRequestDto), VideoSaveRequestDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.youtubeId").isEqualTo(DEFAULT_VIDEO_YOUTUBEID)
                .jsonPath("$.title").isEqualTo(DEFAULT_VIDEO_TITLE)
                .jsonPath("$.contents").isEqualTo(DEFAULT_VIDEO_CONTENTS)
                .jsonPath("$.createDate").isEqualTo(getFormedDate());
    }

    @Test
    void saveVideoTestWhenYoutubeIdError() {
        String youtubeId = null;

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(youtubeId, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        webTestClient.post().uri("/v1/videos")
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
        String title = "";

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, title, DEFAULT_VIDEO_CONTENTS);

        webTestClient.post().uri("/v1/videos")
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
        String contents = " ";

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, contents);

        webTestClient.post().uri("/v1/videos")
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
        webTestClient.get().uri("/v1/videos?filter=date&page=0&limit=5")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void find_video_by_id() {
        saveVideoTest();
        webTestClient.get().uri("/v1/videos/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.youtubeId").isEqualTo(DEFAULT_VIDEO_YOUTUBEID)
                .jsonPath("$.title").isEqualTo(DEFAULT_VIDEO_TITLE)
                .jsonPath("$.contents").isEqualTo(DEFAULT_VIDEO_CONTENTS)
                .jsonPath("$.createDate").isEqualTo(getFormedDate());
    }

    @Test
    void find_video_by_id_exception() {
        webTestClient.get().uri("/v1/videos/100")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.result").isNotEmpty()
                .jsonPath("$.result").isEqualTo("FAIL")
                .jsonPath("$.message").isNotEmpty()
                .jsonPath("$.message").isEqualTo("그런 비디오는 존재하지 않아!");
    }

    private String getFormedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        return now.format(formatter);
    }
}
