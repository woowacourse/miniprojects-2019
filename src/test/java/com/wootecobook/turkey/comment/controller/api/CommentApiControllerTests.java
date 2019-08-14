package com.wootecobook.turkey.comment.controller.api;

import com.wootecobook.turkey.comment.service.CommentService;
import com.wootecobook.turkey.comment.service.dto.CommentCreate;
import com.wootecobook.turkey.comment.service.dto.CommentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentApiControllerTests {
    private static final Long POST_ID = 1L;
    private static final Long COMMENT_ID = 1L;
    private static final String BASE_URI = linkTo(CommentApiController.class, POST_ID).toUri().toString();

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentService commentService;

    @Test
    void 댓글_목록_조회() throws IOException {
        // given
        final Pageable pageable = PageRequest.of(0, 20);
        final Page<CommentResponse> commentResponses = mock(Page.class);

        when(commentService.findCommentResponsesByPostId(anyLong(), any()))
                .thenReturn(commentResponses);

        // when
        final EntityExchangeResult<byte[]> result = webTestClient.get().uri(BASE_URI + "?size={size}&page={page}", pageable.getPageSize(), pageable.getPageNumber())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody().returnResult();

//        final String body = new String(result.getResponseBody());
//        final Page expected = new ObjectMapper().readValue(body, Page.class);

        // then
    }

    @Test
    void 댓글_작성_성공() throws IOException {
        // given
        final CommentCreate commentCreate = new CommentCreate();
        final CommentResponse commentResponse = mock(CommentResponse.class);

        when(commentService.save(any()))
                .thenReturn(commentResponse);
        // when
        final EntityExchangeResult<byte[]> result = webTestClient.post().uri(BASE_URI)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(commentCreate), CommentCreate.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody().returnResult();

//        final String body = new String(result.getResponseBody());
//        final CommentResponse expected = new ObjectMapper().readValue(body, CommentResponse.class);

        // then
    }

    @Test
    void 댓글_삭제_성공() {
        // given

        // when & then
        webTestClient.delete().uri(BASE_URI + "/{id}", COMMENT_ID)
                .exchange()
                .expectStatus().isNoContent()
                .expectHeader().valueMatches("Location", ".*" + BASE_URI);
    }
}