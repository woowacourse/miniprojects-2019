package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.comment.CommentResponseDto;
import com.woowacourse.sunbook.application.service.CommentService;
import com.woowacourse.sunbook.domain.Content;
import com.woowacourse.sunbook.presentation.support.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/size")
    public ResponseEntity<Long> showNumber(@PathVariable final Long articleId) {
        return ResponseEntity.ok().body(commentService.countByArticleId(articleId));
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
    public ResponseEntity<Boolean> remove(@PathVariable final Long articleId,
                                          @PathVariable final Long commentId,
                                          final LoginUser loginUser) {
        return ResponseEntity.ok().body(commentService.remove(commentId, articleId, loginUser.getId()));
    }
}
