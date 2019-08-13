package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.commons.resolver.UserSession;
import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.domain.PostRepository;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import com.wootecobook.turkey.post.service.exception.NotExistPostException;
import com.wootecobook.turkey.user.domain.User;
import com.wootecobook.turkey.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostService postService;

    String testPostContentText;
    Contents testContents;
    Post post;

    @BeforeEach
    void setUp() {
        testPostContentText = "hello";
        testContents = new Contents(testPostContentText);
        post = new Post(testContents, new User());
    }

    @Test
    void post_생성_테스트() {
        when(postRepository.save(post)).thenReturn(post);

        PostResponse result = postService.save(new PostRequest(testPostContentText), new UserSession());

        assertThat(result.getContents()).isEqualTo(testContents);
    }

    @Test
    void post_조회_테스트() {
        long testId = 1L;
        when(postRepository.findById(testId)).thenReturn(Optional.of(post));

        Post testPost = postService.findById(testId);

        assertThat(testPost.getContents()).isEqualTo(testContents);
        assertThrows(NotExistPostException.class, () -> postService.findById(2L));
    }

    @Test
    void post_페이징_테스트() {

    }
}