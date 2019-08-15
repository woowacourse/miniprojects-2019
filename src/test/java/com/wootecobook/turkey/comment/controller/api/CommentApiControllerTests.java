package com.wootecobook.turkey.comment.controller.api;

import com.wootecobook.turkey.comment.domain.CommentAuthException;
import com.wootecobook.turkey.comment.service.dto.CommentCreate;
import com.wootecobook.turkey.comment.service.dto.CommentResponse;
import com.wootecobook.turkey.comment.service.dto.CommentUpdate;
import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import com.wootecobook.turkey.user.controller.BaseControllerTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentApiControllerTests extends BaseControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    private String jSessionId;
    private Long postId;
    private String uri;
    private Long commentId;

    @BeforeEach
    void setUp() {
        final String email = "email@gmail.com";
        final String password = "P@ssw0rd";
        final String name = "name";

        addUser(name, email, password);
        jSessionId = logIn(email, password);

        // TODO 리팩토링
        // 글작성
        PostRequest postRequest = new PostRequest("contents");

        PostResponse postResponse = webTestClient.post().uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(postRequest), PostRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();
        postId = postResponse.getId();

        uri = linkTo(CommentApiController.class, postId).toUri().toString();
        commentId = addComment();
    }

    @Test
    void 댓글_목록_조회() {
        // given
        final Pageable pageable = PageRequest.of(0, 20);

        // when
        final Page expected = webTestClient.get().uri(uri + "?size={size}&page={page}", pageable.getPageSize(), pageable.getPageNumber())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Page.class)
                .returnResult()
                .getResponseBody();
        // then

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
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
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
        assertThat(CommentAuthException.DEFAULT_MESSAGE).isEqualTo(errorMessage.getMessage());
    }

    @Test
    void 답글_저장_성공() {
        // given
        final CommentCreate commentCreate = new CommentCreate();
        commentCreate.setContents("contents");
        commentCreate.setParentId(commentId);

        // when
        final CommentResponse commentResponse = webTestClient.post().uri(uri)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .cookie(JSESSIONID, jSessionId)
                .body(Mono.just(commentCreate), CommentCreate.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectHeader().valueMatches("Location", ".*" + uri)
                .expectBody(CommentResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(commentCreate.getContents()).isEqualTo(commentResponse.getContents());
        assertThat(commentId).isEqualTo(commentResponse.getParent().getId());
    }

    private Long addComment() {
        final CommentCreate commentCreate = new CommentCreate();
        commentCreate.setContents("contents");

        final CommentResponse commentResponse = webTestClient.post().uri(uri)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .cookie(JSESSIONID, jSessionId)
                .body(Mono.just(commentCreate), CommentCreate.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectHeader().valueMatches("Location", ".*" + uri)
                .expectBody(CommentResponse.class)
                .returnResult()
                .getResponseBody();

        return commentResponse.getId();
    }
}