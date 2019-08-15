package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.domain.PostRepository;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import com.wootecobook.turkey.post.service.exception.NotFoundPostException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class PostServiceTest {

    public static final long NOT_FOUND_POST_ID = Long.MAX_VALUE;
    private final PostService postService;

    private Contents testContents;
    private PostRequest postRequest;

    @Autowired
    public PostServiceTest(PostRepository postRepository) {
        this.postService = new PostService(postRepository);
    }

    @BeforeEach
    void setUp() {
        String contentsText = "hello";
        postRequest = new PostRequest(contentsText);
        testContents = new Contents(contentsText);
    }

    @Test
    void post_생성_테스트() {
        PostResponse result = postService.save(postRequest);

        assertThat(result.getContents()).isEqualTo(testContents);
    }

    @Test
    void post_조회_테스트() {
        Long testId = addPost();
        Post testPost = postService.findById(testId);

        assertThat(testPost.getContents()).isEqualTo(testContents);
    }

    @Test
    void 존재하지_않는_post_조회_예외_테스트() {
        assertThrows(NotFoundPostException.class, () -> postService.findById(NOT_FOUND_POST_ID));
    }

    @Test
    void post_페이징_테스트() {
        int pageNum = 0;
        IntStream.rangeClosed(1, 100).forEach(i ->
                postService.save(new PostRequest("hello" + i)));

        Page<PostResponse> pageResponse = postService.findPostResponses(PageRequest.of(pageNum, 10));

        assertThat(pageResponse.getTotalElements()).isEqualTo(100);
        assertThat(pageResponse.getTotalPages()).isEqualTo(10);
        assertThat(pageResponse.getNumber()).isEqualTo(pageNum);

        List<PostResponse> postResponses = pageResponse.getContent();

        for (int i = 1; i <= 10; i++) {
            PostResponse postResponse = postResponses.get(i - 1);
            assertThat(postResponse.getContents()).isEqualTo(new Contents("hello" + i));
        }
    }

    @Test
    void post_수정_테스트() {
        Long testId = addPost();
        PostRequest postRequest = new PostRequest("world!");

        PostResponse updateResult = postService.update(postRequest, testId);

        assertThat(updateResult.getContents()).isEqualTo(new Contents("world!"));
    }

    @Test
    void 없는_게시글_수정_예외_테스트() {
        assertThrows(NotFoundPostException.class, () ->
                postService.update(postRequest, NOT_FOUND_POST_ID));
    }

    @Test
    void post_삭제_테스트() {
        Long testId = addPost();

        assertDoesNotThrow(() -> postService.delete(testId));
    }

    @Test
    void 없는_post_삭제_예외_테스트() {
        assertThrows(NotFoundPostException.class, () -> postService.delete(NOT_FOUND_POST_ID));
    }

    private Long addPost() {
        return postService.save(postRequest).getId();
    }

}