package com.wootecobook.turkey.comment.controller;

import com.wootecobook.turkey.BaseControllerTests;
import com.wootecobook.turkey.comment.domain.exception.NotCommentOwnerException;
import com.wootecobook.turkey.comment.service.dto.CommentCreate;
import com.wootecobook.turkey.comment.service.dto.CommentResponse;
import com.wootecobook.turkey.comment.service.dto.CommentUpdate;
import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.config.AwsMockConfig;
import com.wootecobook.turkey.good.service.dto.GoodResponse;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Import(AwsMockConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentApiControllerTests extends BaseControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    private Long userId;
    private Long commentId;
    private String jSessionId;
    private String uri;

    @BeforeEach
    void setUp() {
        userId = addUser("name", VALID_USER_EMAIL, VALID_USER_PASSWORD);
        jSessionId = logIn(VALID_USER_EMAIL, VALID_USER_PASSWORD);

        //given
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("contents", "contents");

        //when
        PostResponse postResponse = webTestClient.post().uri("/api/posts")
                .cookie(JSESSIONID, jSessionId)
                .syncBody(bodyBuilder.build())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();

        final Long postId = postResponse.getId();
        uri = linkTo(CommentApiController.class, postId).toUri().toString();

        // 댓글 작성
        final CommentCreate commentCreate = new CommentCreate("contents", null);
        commentId = addComment(commentCreate);

        // 답글 작성
        commentCreate.setParentId(commentId);
        addComment(commentCreate);
        addComment(commentCreate);
    }

    @Test
    void 댓글_목록_조회() {
        // given
        final int size = 20;
        final int page = 0;

        // when & then
        webTestClient.get().uri(uri + "?size={size}&page={page}", size, page)
                .accept(MEDIA_TYPE)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MEDIA_TYPE)
                .expectBody()
                .jsonPath("$.size").isEqualTo(size)
                .jsonPath("$.number").isEqualTo(page)
                .jsonPath("$.totalElements").isEqualTo(1);
    }

    @Test
    void 답글만_목록_조회() {
        // given
        final int size = 20;
        final int page = 0;

        // when & then
        webTestClient.get().uri(uri + "/{id}/children?size={size}&page={page}", commentId, size, page)
                .accept(MEDIA_TYPE)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MEDIA_TYPE)
                .expectBody()
                .jsonPath("$.size").isEqualTo(size)
                .jsonPath("$.number").isEqualTo(page)
                .jsonPath("$.totalElements").isEqualTo(2);
    }

    @Test
    void 댓글_삭제_성공() {
        // when & then
        webTestClient.delete().uri(uri + "/{id}", commentId)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isNoContent()
                .expectHeader().valueMatches("Location", ".*" + uri);
    }

    @Test
    void 댓글_수정_성공() {
        // given
        final CommentUpdate commentUpdate = new CommentUpdate();
        commentUpdate.setContents("댓글_수정_성공");

        // when
        final CommentResponse commentResponse = webTestClient.put().uri(uri + "/{id}", commentId)
                .cookie(JSESSIONID, jSessionId)
                .body(Mono.just(commentUpdate), CommentUpdate.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MEDIA_TYPE)
                .expectBody(CommentResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(commentUpdate.getContents()).isEqualTo(commentResponse.getContents());
    }

    @Test
    void 다른_작성자_댓글_수정_예외처리() {
        // given
        final String email = "otheremail@gmail.com";
        final String password = "P@ssw0rd";
        addUser("name", email, password);
        String otherJsessionId = logIn(email, password);

        final CommentUpdate commentUpdate = new CommentUpdate();
        commentUpdate.setContents("다른_작성자_댓글_수정_예외처리");

        // when
        final ErrorMessage errorMessage = webTestClient.put().uri(uri + "/{id}", commentId)
                .cookie(JSESSIONID, otherJsessionId)
                .body(Mono.just(commentUpdate), CommentUpdate.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(NotCommentOwnerException.DEFAULT_MESSAGE).isEqualTo(errorMessage.getMessage());
    }

    @Test
    void 답글_저장_성공() {
        // given
        final CommentCreate commentCreate = new CommentCreate("contents", commentId);

        // when
        final CommentResponse commentResponse = webTestClient.post().uri(uri)
                .accept(MEDIA_TYPE)
                .cookie(JSESSIONID, jSessionId)
                .body(Mono.just(commentCreate), CommentCreate.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MEDIA_TYPE)
                .expectHeader().valueMatches("Location", ".*" + uri)
                .expectBody(CommentResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(commentCreate.getContents()).isEqualTo(commentResponse.getContents());
        assertThat(commentId).isEqualTo(commentResponse.getParentId());
    }

    @Test
    void 댓글_좋아요_및_좋아요_취소_정상_로직() {
        // given & when
        GoodResponse goodResponse = webTestClient.post().uri(uri + "/{id}/good", commentId)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GoodResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(goodResponse.getTotalGood()).isEqualTo(1);

        // given & when
        GoodResponse goodCencelResponse = webTestClient.post().uri(uri + "/{id}/good", commentId)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GoodResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(goodCencelResponse.getTotalGood()).isEqualTo(0);
    }

    private Long addComment(CommentCreate commentCreate) {
        final CommentResponse commentResponse = webTestClient.post().uri(uri)
                .accept(MEDIA_TYPE)
                .cookie(JSESSIONID, jSessionId)
                .body(Mono.just(commentCreate), CommentCreate.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MEDIA_TYPE)
                .expectHeader().valueMatches("Location", ".*" + uri)
                .expectBody(CommentResponse.class)
                .returnResult()
                .getResponseBody();

        return commentResponse.getId();
    }

    @AfterEach
    void tearDown() {
        deleteUser(userId, VALID_USER_EMAIL, VALID_USER_PASSWORD);
    }
}
