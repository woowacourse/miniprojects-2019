package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.EddApplicationTests;
import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.dto.VideoUpdateRequestDto;
import com.woowacourse.edd.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class VideoControllerTests extends EddApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    private final String DEFAULT_VIDEO_YOUTUBEID = "S8e1geEpnTA";
    private final String DEFAULT_VIDEO_TITLE = "제목";
    private final String DEFAULT_VIDEO_CONTENTS = "내용";
    private final String VIDEOS_URI = "/v1/videos";
    private final LocalDateTime DEFAULT_VIDEO_DATETIME = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        save();
    }

    @Test
    void find_video_by_id() {
        findVideo("/1").isOk()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.youtubeId").isEqualTo(DEFAULT_VIDEO_YOUTUBEID)
            .jsonPath("$.title").isEqualTo(DEFAULT_VIDEO_TITLE)
            .jsonPath("$.contents").isEqualTo(DEFAULT_VIDEO_CONTENTS)
            .jsonPath("$.createDate").isEqualTo(Utils.getFormedDate(DEFAULT_VIDEO_DATETIME));
    }

    @Test
    void find_video_by_id_not_found() {
        assertFailNotFound(findVideo("/100"), "그런 비디오는 존재하지 않아!");
    }

    @Test
    void find_videos_by_date() {
        saveVideo(new VideoSaveRequestDto("111", "tilte1", "contents1"));
        saveVideo(new VideoSaveRequestDto("222", "tilte2", "contents2"));
        saveVideo(new VideoSaveRequestDto("333", "tilte3", "contents3"));
        saveVideo(new VideoSaveRequestDto("444", "tilte4", "contents4"));
        saveVideo(new VideoSaveRequestDto("555", "tilte5", "contents5"));
        saveVideo(new VideoSaveRequestDto("666", "tilte6", "contents6"));

        findVideos(0, 6, "createDate", "DESC").isOk().expectBody()
            .jsonPath("$.content.length()").isEqualTo(6)
            .jsonPath("$.content[0].youtubeId").isEqualTo("666")
            .jsonPath("$.content[3].youtubeId").isEqualTo("333")
            .jsonPath("$.content[5].youtubeId").isEqualTo("111");
    }

    @Test
    void save() {
        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        saveVideo(videoSaveRequestDto).isCreated()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.youtubeId").isEqualTo(DEFAULT_VIDEO_YOUTUBEID)
            .jsonPath("$.title").isEqualTo(DEFAULT_VIDEO_TITLE)
            .jsonPath("$.contents").isEqualTo(DEFAULT_VIDEO_CONTENTS)
            .jsonPath("$.createDate").isEqualTo(Utils.getFormedDate(DEFAULT_VIDEO_DATETIME));
    }

    @Test
    void save_invalid_youtube_id() {
        String youtubeId = null;

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(youtubeId, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        assertFailBadRequest(saveVideo(videoSaveRequestDto), "유투브 아이디는 필수로 입력해야합니다.");
    }

    @Test
    void save_empty_title() {
        String title = "";

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, title, DEFAULT_VIDEO_CONTENTS);

        assertFailBadRequest(saveVideo(videoSaveRequestDto), "제목은 한 글자 이상이어야합니다");

    }

    @Test
    void save_invalid_contents() {
        String contents = " ";

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, contents);

        assertFailBadRequest(saveVideo(videoSaveRequestDto), "내용은 한 글자 이상이어야합니다");
    }

    @Test
    void update() {
        save();

        Long id = 2L;
        String youtubeId = "updateYoutubeId";
        String title = "updateTitle";
        String contetns = "updateContents";

        VideoUpdateRequestDto videoUpdateRequestDto = new VideoUpdateRequestDto(youtubeId, title, contetns);

        updateVideo(id, videoUpdateRequestDto)
            .isOk()
            .expectHeader()
            .valueMatches("location", VIDEOS_URI + "/" + id)
            .expectBody()
            .jsonPath("$.id").isEqualTo(id);

    }

    @Test
    void update_invalid_id() {
        Long id = 100L;

        VideoUpdateRequestDto videoUpdateRequestDto = new VideoUpdateRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        assertFailNotFound(updateVideo(id, videoUpdateRequestDto), "그런 비디오는 존재하지 않아!");
    }

    @Test
    void delete() {
        EntityExchangeResult<byte[]> res = saveVideo(new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS))
            .isCreated()
            .expectBody()
            .returnResult();

        String[] id = res.getResponseHeaders().getLocation().toASCIIString().split("/");

        deleteVideo(Long.valueOf(id[id.length - 1])).isNoContent();
    }

    @Test
    void delete_invalid_id() {
        assertFailNotFound(deleteVideo(100L), "그런 비디오는 존재하지 않아!");
    }

    private StatusAssertions findVideo(String uri) {
        return executeGet(VIDEOS_URI + uri)
            .exchange()
            .expectStatus();
    }

    private StatusAssertions findVideos(int page, int size, String sort, String direction) {
        return executeGet(VIDEOS_URI + "?page=" + page + "&size=" + size + "&sort=" + sort + "," + direction)
            .exchange()
            .expectStatus();
    }

    private StatusAssertions saveVideo(VideoSaveRequestDto videoSaveRequestDto) {
        return executePost(VIDEOS_URI)
            .body(Mono.just(videoSaveRequestDto), VideoSaveRequestDto.class)
            .exchange()
            .expectStatus();
    }

    private StatusAssertions updateVideo(Long id, VideoUpdateRequestDto videoUpdateRequestDto) {
        return executePut(VIDEOS_URI + "/" + id)
            .body(Mono.just(videoUpdateRequestDto), VideoUpdateRequestDto.class)
            .exchange()
            .expectStatus();
    }

    private StatusAssertions deleteVideo(Long id) {
        return executeDelete(VIDEOS_URI + "/" + id)
            .exchange()
            .expectStatus();
    }

    private void assertFailBadRequest(StatusAssertions statusAssertions, String errorMessage) {
        WebTestClient.BodyContentSpec bodyContentSpec = statusAssertions
            .isBadRequest()
            .expectBody();

        checkErrorResponse(bodyContentSpec, errorMessage);
    }

    private void assertFailNotFound(StatusAssertions statusAssertions, String errorMessage) {
        WebTestClient.BodyContentSpec bodyContentSpec = statusAssertions
            .isNotFound()
            .expectBody();

        checkErrorResponse(bodyContentSpec, errorMessage);
    }

    private void checkErrorResponse(WebTestClient.BodyContentSpec bodyContentSpec, String errorMessage) {
        bodyContentSpec.jsonPath("$.result").isEqualTo("FAIL")
            .jsonPath("$.message").isEqualTo(errorMessage);
    }

    private WebTestClient.RequestHeadersSpec<?> executeGet(String uri) {
        return webTestClient.get().uri(uri);
    }

    private WebTestClient.RequestBodySpec executePut(String uri) {
        return webTestClient.put().uri(uri);
    }

    private WebTestClient.RequestBodySpec executePost(String uri) {
        return webTestClient.post().uri(uri);
    }

    private WebTestClient.RequestHeadersSpec executeDelete(String uri) {
        return webTestClient.delete().uri(uri);
    }
}
