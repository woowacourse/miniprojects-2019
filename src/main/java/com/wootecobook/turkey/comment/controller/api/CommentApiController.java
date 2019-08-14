package com.wootecobook.turkey.comment.controller.api;

import com.wootecobook.turkey.comment.service.CommentService;
import com.wootecobook.turkey.comment.service.dto.CommentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentApiController {
    private static final Logger log = LoggerFactory.getLogger(CommentApiController.class);

    private final CommentService commentService;

    public CommentApiController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponse>> list(@PageableDefault( sort = "created_at", direction = Sort.Direction.ASC) Pageable pageable,
                                                      @PathVariable Long postId) {
        log.info("postId : {}, page : {}, pageSize : {}, sort : {}", postId, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        final Page<CommentResponse> commentResponses = commentService.findAllByPostId(postId, pageable);

        return ResponseEntity.ok(commentResponses);
    }
}
