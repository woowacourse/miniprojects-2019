package com.wootecobook.turkey.comment.service;

import com.wootecobook.turkey.comment.domain.CommentRepository;
import com.wootecobook.turkey.comment.service.dto.CommentResponse;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.service.PostService;
import com.wootecobook.turkey.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
    private final PostService postService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    public CommentService(final PostService postService, final UserService userService, final CommentRepository commentRepository) {
        this.postService = postService;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> findAllByPostId(final Long postId, final Pageable pageable) {
        final Post post = postService.findById(postId);
        return commentRepository.findAllByPost(post, pageable)
                .map(CommentResponse::from);
    }
}
