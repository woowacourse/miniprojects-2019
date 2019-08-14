package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.domain.PostRepository;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import com.wootecobook.turkey.post.service.exception.NotExistPostException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    private PostService postService;

    private String testPostContentText;
    private Contents testContents;
    private Post post;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository);

        testPostContentText = "hello";
        testContents = new Contents(testPostContentText);
        post = new Post(testContents);
    }

    @Test
    void post_생성_테스트() {
        PostResponse result = postService.save(new PostRequest(testPostContentText));

        assertThat(result.getContents()).isEqualTo(testContents);
    }

    @Test
    void post_조회_테스트() {
        PostResponse savedPost = postService.save(new PostRequest(testPostContentText));
        long testId = savedPost.getId();

        Post testPost = postService.findById(testId);

        assertThat(testPost.getContents()).isEqualTo(testContents);
        assertThrows(NotExistPostException.class, () -> postService.findById(testId + 1));
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
}