package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.domain.PostRepository;
import com.wootecobook.turkey.post.service.dto.PostRequest;
import com.wootecobook.turkey.post.service.dto.PostResponse;
import com.wootecobook.turkey.post.service.exception.NotFoundPostException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponse save(PostRequest postRequest) {
        Post savedPost = postRepository.save(postRequest.toEntity());
        return PostResponse.from(savedPost);
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findPostResponses(final Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::from);
    }

    public PostResponse update(PostRequest postRequest, Long postId) {
        Post post = findById(postId);
        return PostResponse.from(post.update(postRequest.toEntity()));
    }

    public void delete(Long postId) {
        try {
            postRepository.deleteById(postId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundPostException();
        }
    }
}
