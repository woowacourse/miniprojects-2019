package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.dto.VideoUpdateRequestDto;
import com.woowacourse.edd.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class VideoControllerTests extends BasicControllerTests {

    private final String DEFAULT_VIDEO_YOUTUBEID = "S8e1geEpnTA";
    private final String DEFAULT_VIDEO_TITLE = "제목";
    private final String DEFAULT_VIDEO_CONTENTS = "내용";
    private final String VIDEOS_URI = "/v1/videos";
    private final LocalDateTime DEFAULT_VIDEO_DATETIME = LocalDateTime.of(2019, 5, 5, 15, 31, 23);
    private final int DEFAULT_VIDEO_VIEW_COUNT = 100;

    @Test
    void find_video_by_id() {
        findVideo("/" + DEFAULT_VIDEO_ID).isOk()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.youtubeId").isEqualTo(DEFAULT_VIDEO_YOUTUBEID)
            .jsonPath("$.title").isEqualTo(DEFAULT_VIDEO_TITLE)
            .jsonPath("$.contents").isEqualTo(DEFAULT_VIDEO_CONTENTS)
            .jsonPath("$.viewCount").isEqualTo(DEFAULT_VIDEO_VIEW_COUNT)
            .jsonPath("$.createDate").isEqualTo(Utils.getFormedDate(DEFAULT_VIDEO_DATETIME))
            .jsonPath("$.creator.id").isEqualTo(DEFAULT_VIDEO_ID)
            .jsonPath("$.creator.name").isEqualTo(DEFAULT_LOGIN_NAME);
    }

    @Test
    void find_video_by_id_not_found() {
        assertFailNotFound(findVideo("/100"), "그런 비디오는 존재하지 않아!");
    }

    @Test
    void find_videos_by_date() {
        String jsessionid = getDefaultLoginSessionId();
        saveVideo(new VideoSaveRequestDto("111", "tilte1", "contents1"), jsessionid);
        saveVideo(new VideoSaveRequestDto("222", "tilte2", "contents2"), jsessionid);
        saveVideo(new VideoSaveRequestDto("333", "tilte3", "contents3"), jsessionid);
        saveVideo(new VideoSaveRequestDto("444", "tilte4", "contents4"), jsessionid);
        saveVideo(new VideoSaveRequestDto("555", "tilte5", "contents5"), jsessionid);
        saveVideo(new VideoSaveRequestDto("666", "tilte6", "contents6"), jsessionid);

        findVideos(0, 6, "createDate", "DESC").isOk().expectBody()
            .jsonPath("$.content.length()").isEqualTo(6)
            .jsonPath("$.content[0].youtubeId").isEqualTo("666")
            .jsonPath("$.content[0].viewCount").isEqualTo(0)
            .jsonPath("$.content[3].youtubeId").isEqualTo("333")
            .jsonPath("$.content[5].youtubeId").isEqualTo("111")
            .jsonPath("$.content[5].creator.id").isEqualTo(DEFAULT_LOGIN_ID);
    }

    @Test
    void save() {
        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        saveVideo(videoSaveRequestDto, getDefaultLoginSessionId()).isCreated()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.youtubeId").isEqualTo(DEFAULT_VIDEO_YOUTUBEID)
            .jsonPath("$.title").isEqualTo(DEFAULT_VIDEO_TITLE)
            .jsonPath("$.contents").isEqualTo(DEFAULT_VIDEO_CONTENTS)
            .jsonPath("$.createDate").isEqualTo(Utils.getFormedDate(LocalDateTime.now(ZoneId.of("UTC"))))
            .jsonPath("$.creator.id").isEqualTo(DEFAULT_LOGIN_ID)
            .jsonPath("$.creator.name").isEqualTo(DEFAULT_LOGIN_NAME);
    }

    @Test
    void save_invalid_youtube_id() {
        String youtubeId = null;

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(youtubeId, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        assertFailBadRequest(saveVideo(videoSaveRequestDto, getDefaultLoginSessionId()), "유투브 아이디는 필수로 입력해야합니다.");
    }

    @Test
    void save_empty_title() {
        String title = "";

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, title, DEFAULT_VIDEO_CONTENTS);

        assertFailBadRequest(saveVideo(videoSaveRequestDto, getDefaultLoginSessionId()), "제목은 한 글자 이상이어야합니다");

    }

    @Test
    void save_invalid_contents() {
        String contents = " ";

        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, contents);

        assertFailBadRequest(saveVideo(videoSaveRequestDto, getDefaultLoginSessionId()), "내용은 한 글자 이상이어야합니다");
    }

    @Test
    void update() {
        save();

        Long id = 2L;
        String youtubeId = "updateYoutubeId";
        String title = "updateTitle";
        String contetns = "updateContents";

        VideoUpdateRequestDto videoUpdateRequestDto = new VideoUpdateRequestDto(youtubeId, title, contetns);

        updateVideo(id, videoUpdateRequestDto, getDefaultLoginSessionId())
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

        assertFailNotFound(updateVideo(id, videoUpdateRequestDto, getDefaultLoginSessionId()), "그런 비디오는 존재하지 않아!");
    }

    @Test
    void delete() {
        EntityExchangeResult<byte[]> res = saveVideo(new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS), getDefaultLoginSessionId())
            .isCreated()
            .expectBody()
            .returnResult();

        String[] id = res.getResponseHeaders().getLocation().toASCIIString().split("/");

        deleteVideo(Long.valueOf(id[id.length - 1]), getDefaultLoginSessionId()).isNoContent();
    }

    @Test
    void delete_invalid_id() {
        assertFailNotFound(deleteVideo(100L, getDefaultLoginSessionId()), "그런 비디오는 존재하지 않아!");
    }

    private StatusAssertions findVideo(String uri) {
        return executeGet(VIDEOS_URI + uri)
            .exchange()
            .expectStatus();
    }

    private StatusAssertions findVideos(int page, int size, String sort, String direction) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.path(VIDEOS_URI)
            .query("page=" + page)
            .query("size=" + size)
            .query("sort=" + sort + "," + direction);
        String uri = builder.build().toUriString();
        return executeGet(uri)
            .exchange()
            .expectStatus();
    }

    private StatusAssertions saveVideo(VideoSaveRequestDto videoSaveRequestDto, String jsessionid) {
        return executePost(VIDEOS_URI)
            .cookie(COOKIE_JSESSIONID, jsessionid)
            .body(Mono.just(videoSaveRequestDto), VideoSaveRequestDto.class)
            .exchange()
            .expectStatus();
    }

    private StatusAssertions updateVideo(Long id, VideoUpdateRequestDto videoUpdateRequestDto, String jsessionid) {
        return executePut(VIDEOS_URI + "/" + id)
            .cookie(COOKIE_JSESSIONID, jsessionid)
            .body(Mono.just(videoUpdateRequestDto), VideoUpdateRequestDto.class)
            .exchange()
            .expectStatus();
    }

    private StatusAssertions deleteVideo(Long id, String jsessionid) {
        return executeDelete(VIDEOS_URI + "/" + id)
            .cookie(COOKIE_JSESSIONID, jsessionid)
            .exchange()
            .expectStatus();
    }
}
