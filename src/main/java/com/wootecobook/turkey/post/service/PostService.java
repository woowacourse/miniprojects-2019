package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.domain.PostRepository;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import com.wootecobook.turkey.post.service.exception.NotExistPostException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponse save(PostRequest postRequest) {
        Post savedPost = postRepository.save(postRequest.toEntity());
        return PostResponse.from(savedPost);
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(NotExistPostException::new);
    }

    public Page<PostResponse> findPostResponses(final Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::from);
    }

    public PostResponse update(PostRequest postRequest, Long postId) {
        Post post = findById(postId);
        return PostResponse.from(post.update(postRequest.toEntity()));
    }

    public void delete(Long postId) {
        Post post = findById(postId);
        postRepository.delete(post);
    }
}
