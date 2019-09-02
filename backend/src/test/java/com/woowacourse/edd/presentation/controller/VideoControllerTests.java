package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.dto.UserSaveRequestDto;
import com.woowacourse.edd.application.dto.VideoSaveRequestDto;
import com.woowacourse.edd.application.dto.VideoUpdateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static com.woowacourse.edd.application.dto.VideoSaveRequestDto.OVER_SIZE_CONTENTS_MESSAGE;
import static com.woowacourse.edd.application.dto.VideoSaveRequestDto.OVER_SIZE_TITLE_MESSAGE;
import static com.woowacourse.edd.application.dto.VideoSaveRequestDto.OVER_SIZE_YOUTUBEID_MESSAGE;
import static com.woowacourse.edd.exceptions.UnauthorizedAccessException.UNAUTHORIZED_ACCESS_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

public class VideoControllerTests extends BasicControllerTests {

    private final String DEFAULT_VIDEO_YOUTUBEID = "S8e1geEpnTA";
    private final String DEFAULT_VIDEO_TITLE = "제목";
    private final String DEFAULT_VIDEO_CONTENTS = "내용";
    private final String VIDEOS_URI = "/v1/videos";
    private final int DEFAULT_VIDEO_VIEW_COUNT = 100;

    @Test
    void find_video_by_id() {
        findVideo("/" + DEFAULT_VIDEO_ID)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.youtubeId").isEqualTo(DEFAULT_VIDEO_YOUTUBEID)
            .jsonPath("$.title").isEqualTo(DEFAULT_VIDEO_TITLE)
            .jsonPath("$.contents").isEqualTo(DEFAULT_VIDEO_CONTENTS)
            .jsonPath("$.viewCount").isEqualTo(DEFAULT_VIDEO_VIEW_COUNT + 1)
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

        saveVideo(new VideoSaveRequestDto("111", "title1", "contents1"), jsessionid);
        saveVideo(new VideoSaveRequestDto("222", "title2", "contents2"), jsessionid);
        saveVideo(new VideoSaveRequestDto("333", "title2", "contents3"), jsessionid);
        saveVideo(new VideoSaveRequestDto("444", "title4", "contents4"), jsessionid);
        saveVideo(new VideoSaveRequestDto("555", "title5", "contents5"), jsessionid);
        saveVideo(new VideoSaveRequestDto("666", "title6", "contents6"), jsessionid);

        PageRequestContentDto response = findVideos(0, 6, "createDate", "DESC")
            .expectStatus().isOk()
            .expectBody(PageRequestContentDto.class).returnResult().getResponseBody();

        IntStream.range(0, response.getContent().size() - 1)
            .forEach(i -> {
                assertThat(LocalDateTime.parse((String) response.getContent().get(i).get("createDate")))
                    .isAfterOrEqualTo(LocalDateTime.parse((String) response.getContent().get(i + 1).get("createDate")));
            });
    }

    @Test
    void find_videos_by_creator() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("edan", "edan1000@gmail.com", "p@ssW0rd", "p@ssW0rd");
        String url = signUp(userSaveRequestDto).getResponseHeaders()
            .getLocation()
            .toASCIIString();

        LoginRequestDto loginRequestDto = new LoginRequestDto("edan1000@gmail.com", "p@ssW0rd");
        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto("abc", "newtitle", "newContents");
        VideoSaveRequestDto secondVideoSaveRequestDto = new VideoSaveRequestDto("def", "secondtitle", "secondcontents");

        String cookie = getLoginCookie(loginRequestDto);
        String[] urls = url.split("/");
        Long userId = Long.valueOf(urls[urls.length - 1]);

        saveVideo(videoSaveRequestDto, cookie);
        saveVideo(secondVideoSaveRequestDto, cookie);

        findVideo("/creators/" + userId)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(2)
            .jsonPath("$[0].creator.id").isEqualTo(userId)
            .jsonPath("$[1].creator.id").isEqualTo(userId)
            .jsonPath("$[0].title").isEqualTo("newtitle")
            .jsonPath("$[1].title").isEqualTo("secondtitle");
    }

    @Test
    void save() {
        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        saveVideo(videoSaveRequestDto, getDefaultLoginSessionId())
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.youtubeId").isEqualTo(DEFAULT_VIDEO_YOUTUBEID)
            .jsonPath("$.title").isEqualTo(DEFAULT_VIDEO_TITLE)
            .jsonPath("$.contents").isEqualTo(DEFAULT_VIDEO_CONTENTS)
            .jsonPath("$.creator.id").isEqualTo(DEFAULT_LOGIN_ID)
            .jsonPath("$.creator.name").isEqualTo(DEFAULT_LOGIN_NAME);
    }

    @Test
    void save_oversize_youtubeId() {
        String overSizeYoutubeId = getOverSizeString(256);
        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(overSizeYoutubeId, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        assertFailBadRequest(saveVideo(videoSaveRequestDto, getDefaultLoginSessionId()), OVER_SIZE_YOUTUBEID_MESSAGE);
    }

    @Test
    void save_oversize_title() {
        String oversizeTitle = getOverSizeString(81);
        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, oversizeTitle, DEFAULT_VIDEO_CONTENTS);

        assertFailBadRequest(saveVideo(videoSaveRequestDto, getDefaultLoginSessionId()), OVER_SIZE_TITLE_MESSAGE);
    }

    @Test
    void save_oversize_contents() {
        String overSizeContents = getOverSizeString(256);
        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, overSizeContents);

        assertFailBadRequest(saveVideo(videoSaveRequestDto, getDefaultLoginSessionId()), OVER_SIZE_CONTENTS_MESSAGE);
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
        String jsessionid = getDefaultLoginSessionId();
        VideoSaveRequestDto videoSaveRequestDto = new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        String returnUrl = saveVideo(videoSaveRequestDto, jsessionid)
            .expectStatus().isCreated()
            .expectBody()
            .returnResult()
            .getResponseHeaders()
            .getLocation()
            .toASCIIString();

        String[] urls = returnUrl.split("/");
        Long id = Long.valueOf(urls[urls.length - 1]);
        String youtubeId = "updateYoutubeId";
        String title = "updateTitle";
        String contetns = "updateContents";

        VideoUpdateRequestDto videoUpdateRequestDto = new VideoUpdateRequestDto(youtubeId, title, contetns);

        updateVideo(id, videoUpdateRequestDto, jsessionid)
            .expectStatus().isOk()
            .expectHeader()
            .valueMatches("location", returnUrl)
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
    void update_invalid_user() {
        String updateInvalidEmail = "updateVideo@email.com";
        String updateInvalidPW = "P@ssw0rd";

        signUp(new UserSaveRequestDto("name", updateInvalidEmail, updateInvalidPW, updateInvalidPW));

        VideoUpdateRequestDto videoUpdateRequestDto = new VideoUpdateRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS);

        assertFailForbidden(updateVideo(DEFAULT_VIDEO_ID, videoUpdateRequestDto, getLoginCookie(new LoginRequestDto(updateInvalidEmail, updateInvalidPW))), UNAUTHORIZED_ACCESS_MESSAGE);
    }

    @Test
    void update_view_count() {
        String sid = getDefaultLoginSessionId();
        String location = saveVideo(new VideoSaveRequestDto("abcdefg", "videos", "good video"), sid)
            .expectStatus().isCreated()
            .expectBody().returnResult().getResponseHeaders().getLocation().toASCIIString();

        webTestClient.get().uri(location)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.viewCount").isEqualTo(1)
            .consumeWith(res -> {
                webTestClient.get().uri(location)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.viewCount").isEqualTo(2);
            });
    }

    @Test
    void delete() {
        EntityExchangeResult<byte[]> res = saveVideo(new VideoSaveRequestDto(DEFAULT_VIDEO_YOUTUBEID, DEFAULT_VIDEO_TITLE, DEFAULT_VIDEO_CONTENTS), getDefaultLoginSessionId())
            .expectStatus()
            .isCreated()
            .expectBody()
            .returnResult();

        String[] id = res.getResponseHeaders().getLocation().toASCIIString().split("/");

        deleteVideo(Long.valueOf(id[id.length - 1]), getDefaultLoginSessionId())
            .expectStatus()
            .isNoContent();
    }

    @Test
    void delete_invalid_id() {
        assertFailNotFound(deleteVideo(100L, getDefaultLoginSessionId()), "그런 비디오는 존재하지 않아!");
    }

    @Test
    void delete_invalid_user() {
        String deleteVideoEmail = "deleteVideo@email.com";
        String deleteVideoPW = "P@ssw0rd";
        signUp(new UserSaveRequestDto("name", deleteVideoEmail, deleteVideoPW, deleteVideoPW));

        assertFailForbidden(deleteVideo(DEFAULT_VIDEO_ID, getLoginCookie(new LoginRequestDto(deleteVideoEmail, deleteVideoPW))), UNAUTHORIZED_ACCESS_MESSAGE);
    }

    private WebTestClient.ResponseSpec findVideo(String uri) {
        return executeGet(VIDEOS_URI + uri)
            .exchange();
    }

    private WebTestClient.ResponseSpec findVideos(int page, int size, String sort, String direction) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.path(VIDEOS_URI)
            .query("page=" + page)
            .query("size=" + size)
            .query("sort=" + sort + "," + direction);
        String uri = builder.build().toUriString();
        return executeGet(uri)
            .exchange();
    }

    private WebTestClient.ResponseSpec saveVideo(VideoSaveRequestDto videoSaveRequestDto, String jsessionid) {
        return executePost(VIDEOS_URI)
            .cookie(COOKIE_JSESSIONID, jsessionid)
            .body(Mono.just(videoSaveRequestDto), VideoSaveRequestDto.class)
            .exchange();
    }

    private WebTestClient.ResponseSpec updateVideo(Long id, VideoUpdateRequestDto videoUpdateRequestDto, String jsessionid) {
        return executePut(VIDEOS_URI + "/" + id)
            .cookie(COOKIE_JSESSIONID, jsessionid)
            .body(Mono.just(videoUpdateRequestDto), VideoUpdateRequestDto.class)
            .exchange();
    }

    private WebTestClient.ResponseSpec deleteVideo(Long id, String jsessionid) {
        return executeDelete(VIDEOS_URI + "/" + id)
            .cookie(COOKIE_JSESSIONID, jsessionid)
            .exchange();
    }

    private WebTestClient.BodyContentSpec saveNextVideo(VideoSaveRequestDto video, String sid) {
        return saveVideo(video, sid)
            .expectStatus().isCreated()
            .expectBody();
    }
}
