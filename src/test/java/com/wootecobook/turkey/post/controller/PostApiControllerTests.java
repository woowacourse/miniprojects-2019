package com.wootecobook.turkey.post.controller;

import com.wootecobook.turkey.BaseControllerTests;
import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.commons.GoodResponse;
import com.wootecobook.turkey.config.AwsMockConfig;
import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@Import(AwsMockConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTests extends BaseControllerTests {

    public static final String POST_URL = "/api/posts";

    @Autowired
    private WebTestClient webTestClient;

    private Long userId;
    private String jSessionId;
    private ByteArrayResource testFile;

    @BeforeEach
    void setUp() {
        userId = addUser("olaf", VALID_USER_EMAIL, VALID_USER_PASSWORD);
        jSessionId = logIn(VALID_USER_EMAIL, VALID_USER_PASSWORD);
        testFile = new ByteArrayResource(new byte[]{1, 2, 3, 4}) {
            @Override
            public String getFilename() {
                return "test_file.mp4";
            }
        };
    }

    @Test
    void 파일_첨부하고_post_생성_정상_로직_테스트() {
        //given
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("files", testFile, MediaType.parseMediaType("image/jpeg"));
        bodyBuilder.part("files", testFile, MediaType.parseMediaType("image/jpeg"));
        bodyBuilder.part("contents", "hello");

        //when
        PostResponse postResponse = webTestClient.post().uri(POST_URL)
                .cookie(JSESSIONID, jSessionId)
                .syncBody(bodyBuilder.build())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(postResponse.getContents()).isEqualTo(new Contents("hello"));
        assertThat(postResponse.getFiles().size()).isEqualTo(2);
    }

    @Test
    void 파일_첨부없이_post_생성_정상_로직_테스트() {
        //given
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("contents", "hello");

        //when
        PostResponse postResponse = webTestClient.post().uri(POST_URL)
                .cookie(JSESSIONID, jSessionId)
                .syncBody(bodyBuilder.build())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(postResponse.getContents()).isEqualTo(new Contents("hello"));
        assertThat(postResponse.getFiles().size()).isEqualTo(0);
    }

    @Test
    void post_생성_contents가_빈값인_경우_예외_테스트() {
        //given
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("contents", "");

        //when
        ErrorMessage errorMessage = webTestClient.post().uri(POST_URL)
                .cookie(JSESSIONID, jSessionId)
                .syncBody(bodyBuilder.build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(errorMessage).isNotNull();
    }

    @Test
    void 페이지_조회_정상_로직_테스트() {
        //when & then
        webTestClient.get().uri(POST_URL)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 게시글_수정_정상_로직_테스트() {
        //given
        Long postId = addPost("olaf");

        PostRequest postUpdateRequest = PostRequest.builder().contents("chelsea").build();

        //when
        PostResponse postResponse = webTestClient.put().uri(POST_URL + "/{postId}", postId)
                .cookie(JSESSIONID, jSessionId)
                .contentType(MEDIA_TYPE)
                .body(Mono.just(postUpdateRequest), PostRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(postResponse.getContents()).isEqualTo(new Contents("chelsea"));
    }

    @Test
    void 게시글_수정_contents가_빈_내용인_경우_예외_테스트() {
        //given
        Long postId = addPost("olaf");

        PostRequest postUpdateRequest = PostRequest.builder().contents("").build();

        //when & then
        webTestClient.put().uri(POST_URL + "/{postId}", postId)
                .cookie(JSESSIONID, jSessionId)
                .contentType(MEDIA_TYPE)
                .body(Mono.just(postUpdateRequest), PostRequest.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void 게시글_삭제_정상_로직_테스트() {
        //given
        Long postId = addPost("olaf");

        //when & then
        webTestClient.delete().uri(POST_URL + "/{postId}", postId)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void 게시글_삭제_존재하지_않는_게시글_예외_테스트() {
        //when & then
        webTestClient.delete().uri(POST_URL + "/{postId}", Long.MAX_VALUE)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void 게시글_좋아요_생성_삭제_테스트() {
        // given
        Long postId = addPost("olaf");

        // when
        GoodResponse goodResponse = webTestClient.get().uri(POST_URL + "/{postId}/good", postId)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GoodResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(goodResponse.getTotalGood()).isEqualTo(1);

        // when
        GoodResponse postGoodCencelResponse = webTestClient.get().uri(POST_URL + "/{postId}/good", postId)
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GoodResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(postGoodCencelResponse.getTotalGood()).isEqualTo(0);
    }


    private Long addPost(String contents) {
        //given
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("contents", contents);

        PostResponse postResponse = webTestClient.post().uri(POST_URL)
                .cookie(JSESSIONID, jSessionId)
                .syncBody(bodyBuilder.build())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();

        return postResponse.getId();
    }

    @AfterEach
    void tearDown() {
        deleteUser(userId, VALID_USER_EMAIL, VALID_USER_PASSWORD);
    }
}