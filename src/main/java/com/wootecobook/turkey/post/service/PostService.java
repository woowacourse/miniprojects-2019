package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.commons.resolver.UserSession;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.domain.PostRepository;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import com.wootecobook.turkey.post.service.exception.NotExistPostException;
import com.wootecobook.turkey.user.domain.User;
import com.wootecobook.turkey.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private static final String NOT_EXIST_POST_ERROR_MESSAGE = "존재하지 않는 포스트입니다.";

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(final PostRepository postRepository, final UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public PostResponse save(PostRequest postRequest, UserSession userSession) {
        //User user = userService.findById(userSession.getUserId());

        Post post = postRequest.toEntity(new User());
        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotExistPostException(NOT_EXIST_POST_ERROR_MESSAGE));
    }

    public Page<PostResponse> findPostResponses(final Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::from);
    }
}
