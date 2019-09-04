package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.CommentRequestDto;
import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.dto.UserSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.edd.application.dto.VideoSaveRequestDto.OVER_SIZE_CONTENTS_MESSAGE;
import static com.woowacourse.edd.exceptions.CommentNotFoundException.COMMENT_NOT_FOUND_MESSAGE;
import static com.woowacourse.edd.exceptions.InvalidAccessException.INVALID_ACCESS_MESSAGE;
import static com.woowacourse.edd.exceptions.InvalidContentsException.INVALID_CONTENTS_MESSAGE;
import static com.woowacourse.edd.exceptions.UnauthorizedAccessException.UNAUTHORIZED_ACCESS_MESSAGE;
import static com.woowacourse.edd.presentation.controller.VideoController.VIDEO_URL;

public class CommentControllerTests extends BasicControllerTests {

    private static final String COMMENT_URL = "/comments";
    private static final String DEFAULT_CONTENTS = "contents";
    private String cookie;

    @BeforeEach
    void setUp() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(DEFAULT_LOGIN_EMAIL, DEFAULT_LOGIN_PASSWORD);
        cookie = getLoginCookie(loginRequestDto);
    }

    @Test
    void save() {
        CommentRequestDto commentRequestDto = new CommentRequestDto(DEFAULT_CONTENTS);
        saveComment(DEFAULT_VIDEO_ID, commentRequestDto)
            .expectStatus().isCreated()
            .expectHeader()
            .exists("location")
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.contents").isEqualTo(DEFAULT_CONTENTS)
            .jsonPath("$.author.id").isEqualTo(1L)
            .jsonPath("$.author.name").isEqualTo(DEFAULT_LOGIN_NAME)
            .jsonPath("$.createDate").isNotEmpty();
    }

    @Test
    void save_invalid_contents() {
        CommentRequestDto commentRequestDto = new CommentRequestDto(" ");
        assertFailBadRequest(saveComment(DEFAULT_VIDEO_ID, commentRequestDto), INVALID_CONTENTS_MESSAGE);
    }

    @Test
    void save_oversize_contents() {
        String overSizeContents = "";

        for (int i = 0; i < 256; i++) {
            overSizeContents += "a";
        }
        CommentRequestDto commentRequestDto = new CommentRequestDto(overSizeContents);
        assertFailBadRequest(saveComment(DEFAULT_VIDEO_ID, commentRequestDto), OVER_SIZE_CONTENTS_MESSAGE);

    }

    @Test
    void retrieveComments() {
        getComments(DEFAULT_VIDEO_ID)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$[0].id").isEqualTo(1L)
            .jsonPath("$[0].contents").isEqualTo("contents")
            .jsonPath("$[0].author.id").isEqualTo(1L)
            .jsonPath("$[0].author.name").isEqualTo(DEFAULT_LOGIN_NAME)
            .jsonPath("$[0].createDate").isNotEmpty();
    }

    @Test
    void retrieveComments_invalid_videoId() {
        getComments(100L)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(0L);
    }

    @Test
    void update() {
        String preContents = "secondContents";
        String updateContents = "updateContents";
        CommentRequestDto saveCommentRequestDto = new CommentRequestDto(preContents);
        String returnUrl = getSaveUrl(preContents, saveCommentRequestDto);

        CommentRequestDto updateCommentRequestDto = new CommentRequestDto(updateContents);
        updateComment(returnUrl, updateCommentRequestDto)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.contents").isEqualTo(updateContents)
            .jsonPath("$.author.id").isEqualTo(1L)
            .jsonPath("$.author.name").isEqualTo(DEFAULT_LOGIN_NAME)
            .jsonPath("$.createDate").isNotEmpty();
    }

    @Test
    void update_invalid_videoId() {
        String preContents = "secondContents";
        String updateContents = "updateContents";
        CommentRequestDto saveCommentRequestDto = new CommentRequestDto(preContents);
        String returnUrl = getSaveUrl(preContents, saveCommentRequestDto);

        String[] urls = returnUrl.split("/");
        Long commentId = Long.valueOf(urls[urls.length - 1]);

        CommentRequestDto updateCommentRequestDto = new CommentRequestDto(updateContents);

        assertFailBadRequest(updateComment(DEFAULT_VIDEO_ID + 1L, commentId, updateCommentRequestDto, cookie),
            INVALID_ACCESS_MESSAGE);
    }

    @Test
    void update_invalid_user() {
        String invalidEmail = "heebong@email.com";
        String invalidPW = "P@ssw0rd";

        UserSaveRequestDto invalidUserSaveRequestDto = new UserSaveRequestDto("heebong", invalidEmail, invalidPW, invalidPW);
        LoginRequestDto invalidUserLoginRequestDto = new LoginRequestDto(invalidEmail, invalidPW);
        signUp(invalidUserSaveRequestDto);
        String cookie = getLoginCookie(invalidUserLoginRequestDto);

        String preContents = "secondContents";
        String updateContents = "updateContents";
        CommentRequestDto saveCommentRequestDto = new CommentRequestDto(preContents);
        String returnUrl = getSaveUrl(preContents, saveCommentRequestDto);

        String[] urls = returnUrl.split("/");
        Long commentId = Long.valueOf(urls[urls.length - 1]);

        CommentRequestDto updateCommentRequestDto = new CommentRequestDto(updateContents);

        assertFailForbidden(updateComment(DEFAULT_VIDEO_ID, commentId, updateCommentRequestDto, cookie)
            , UNAUTHORIZED_ACCESS_MESSAGE);
    }

    @Test
    void update_invalid_commentId() {
        String preContents = "secondContents";
        String updateContents = "updateContents";
        CommentRequestDto saveCommentRequestDto = new CommentRequestDto(preContents);
        String returnUrl = getSaveUrl(preContents, saveCommentRequestDto);

        String[] urls = returnUrl.split("/");
        Long commentId = Long.valueOf(urls[urls.length - 1]);

        CommentRequestDto updateCommentRequestDto = new CommentRequestDto(updateContents);

        assertFailNotFound(updateComment(DEFAULT_VIDEO_ID, commentId + 100L, updateCommentRequestDto, cookie)
            , COMMENT_NOT_FOUND_MESSAGE);
    }

    @Test
    void delete() {
        CommentRequestDto commentRequestDto = new CommentRequestDto("contents");
        String returnUrl = saveComment(DEFAULT_VIDEO_ID, commentRequestDto)
            .expectBody()
            .returnResult()
            .getResponseHeaders()
            .getLocation()
            .toASCIIString();

        Long commentId = getPathVariable(returnUrl);
        executeDelete(VIDEO_URL + "/" + DEFAULT_VIDEO_ID + COMMENT_URL + "/" + commentId)
            .cookie(COOKIE_JSESSIONID, cookie)
            .exchange()
            .expectStatus()
            .isNoContent();
    }

    @Test
    void delete_invalid_commentId() {
        CommentRequestDto commentRequestDto = new CommentRequestDto("contents");
        String returnUrl = saveComment(DEFAULT_VIDEO_ID, commentRequestDto)
            .expectBody()
            .returnResult()
            .getResponseHeaders()
            .getLocation()
            .toASCIIString();

        Long commentId = getPathVariable(returnUrl);
        assertFailNotFound(deleteComment(commentId + 100L, DEFAULT_VIDEO_ID, cookie)
            , COMMENT_NOT_FOUND_MESSAGE);
    }

    @Test
    void delete_invalid_videoId() {
        CommentRequestDto commentRequestDto = new CommentRequestDto("contents");
        String returnUrl = saveComment(DEFAULT_VIDEO_ID, commentRequestDto)
            .expectBody()
            .returnResult()
            .getResponseHeaders()
            .getLocation()
            .toASCIIString();

        Long commentId = getPathVariable(returnUrl);
        assertFailBadRequest(deleteComment(commentId, DEFAULT_VIDEO_ID + 100L, cookie)
            , INVALID_ACCESS_MESSAGE);
    }

    @Test
    void delete_invalid_userId() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto("heebong", "heebong@naver.com", "P@ssW0rd", "P@ssW0rd");
        signUp(userSaveRequestDto);
        String cookie = getLoginCookie(new LoginRequestDto("heebong@naver.com", "P@ssW0rd"));

        CommentRequestDto commentRequestDto = new CommentRequestDto("contents");
        String returnUrl = saveComment(DEFAULT_VIDEO_ID, commentRequestDto)
            .expectBody()
            .returnResult()
            .getResponseHeaders()
            .getLocation()
            .toASCIIString();

        Long commentId = getPathVariable(returnUrl);
        assertFailForbidden(deleteComment(commentId, DEFAULT_VIDEO_ID + 100L, cookie)
            , UNAUTHORIZED_ACCESS_MESSAGE);
    }

    private String getSaveUrl(String preContents, CommentRequestDto saveCommentRequestDto) {
        return saveComment(DEFAULT_VIDEO_ID, saveCommentRequestDto)
            .expectBody()
            .jsonPath("$.contents").isEqualTo(preContents)
            .returnResult()
            .getResponseHeaders()
            .getLocation()
            .toASCIIString();
    }

    private WebTestClient.ResponseSpec deleteComment(Long commentId, Long videoId, String cookie) {
        return executeDelete(VIDEO_URL + "/" + videoId + COMMENT_URL + "/" + commentId)
            .cookie(COOKIE_JSESSIONID, cookie)
            .exchange();
    }


    private Long getPathVariable(String returnUrl) {
        String[] urls = returnUrl.split("/");
        return Long.valueOf(urls[urls.length - 1]);
    }

    private WebTestClient.ResponseSpec saveComment(Long videoId, CommentRequestDto commentRequestDto) {
        return executePost(VIDEO_URL + "/" + videoId + COMMENT_URL)
            .cookie(COOKIE_JSESSIONID, cookie)
            .body(Mono.just(commentRequestDto), CommentRequestDto.class)
            .exchange();
    }

    private WebTestClient.ResponseSpec getComments(Long videoId) {
        return executeGet(VIDEO_URL + "/" + videoId + COMMENT_URL)
            .exchange();
    }

    private WebTestClient.ResponseSpec updateComment(String returnUrl, CommentRequestDto updateCommentRequestDto) {
        return executePut(returnUrl)
            .cookie(COOKIE_JSESSIONID, cookie)
            .body(Mono.just(updateCommentRequestDto), CommentRequestDto.class)
            .exchange();
    }

    private WebTestClient.ResponseSpec updateComment(Long videoId, Long commentId, CommentRequestDto updateCommentRequestDto, String cookie) {
        return executePut(VIDEO_URL + "/" + videoId + COMMENT_URL + "/" + commentId)
            .cookie(COOKIE_JSESSIONID, cookie)
            .body(Mono.just(updateCommentRequestDto), CommentRequestDto.class)
            .exchange();
    }
}
