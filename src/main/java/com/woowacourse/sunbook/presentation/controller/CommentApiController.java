package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.comment.CommentResponseDto;
import com.woowacourse.sunbook.application.service.CommentService;
import com.woowacourse.sunbook.domain.Content;
import com.woowacourse.sunbook.presentation.support.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
public class CommentApiController {
    private final CommentService commentService;

    public CommentApiController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = {"", "/{commentId}"})
    public ResponseEntity<List<CommentResponseDto>> show(@PathVariable final Long articleId,
                                                         @PathVariable(required = false) final Long commentId) {
        List<CommentResponseDto> commentResponseDto = commentService.findByIdAndArticleId(articleId, commentId);

        return ResponseEntity.ok().body(commentResponseDto);
    }

    @PostMapping(value = {"", "/{commentId}"})
    public ResponseEntity<CommentResponseDto> save(@PathVariable final Long articleId,
                                                   @PathVariable(required = false) final Long commentId,
                                                   final LoginUser loginUser,
                                                   @RequestBody final Content content) {
        CommentResponseDto commentResponseDto = commentService.save(content, articleId, loginUser.getId(), commentId);

        return ResponseEntity.ok().body(commentResponseDto);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> modify(@PathVariable final Long articleId,
                                                     @PathVariable final Long commentId,
                                                     final LoginUser loginUser,
                                                     @RequestBody final Content content) {
        CommentResponseDto commentResponseDto = commentService.modify(commentId, content, articleId, loginUser.getId());

        return ResponseEntity.ok().body(commentResponseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> remove(@PathVariable final Long articleId,
                                       @PathVariable final Long commentId,
                                       final LoginUser loginUser) {
        commentService.remove(commentId, articleId, loginUser.getId());

        return ResponseEntity.ok().build();
    }
}
