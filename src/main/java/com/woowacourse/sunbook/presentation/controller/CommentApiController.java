package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.comment.CommentResponseDto;
import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.service.CommentService;
import com.woowacourse.sunbook.domain.comment.CommentFeature;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> show(@PathVariable final Long articleId) {
        List<CommentResponseDto> commentResponseDto = commentService.findByArticleId(articleId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @PostMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> save(@PathVariable final Long articleId,
                                                   @PathVariable(required = false) final Long commentId,
                                                   @SessionAttribute("loginUser") final UserResponseDto userResponseDto,
                                                   @RequestBody final CommentFeature commentFeature) {
        CommentResponseDto commentResponseDto = commentService.save(commentFeature, articleId, userResponseDto.getId(), commentId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> modify(@PathVariable final Long articleId,
                                                     @PathVariable final Long commentId,
                                                     @SessionAttribute("loginUser") final UserResponseDto userResponseDto,
                                                     @RequestBody final CommentFeature commentFeature) {
        CommentResponseDto commentResponseDto = commentService.modify(commentId, commentFeature, articleId, userResponseDto.getId());

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> remove(@PathVariable final Long articleId,
                                       @PathVariable final Long commentId,
                                       @SessionAttribute("loginUser") final UserResponseDto userResponseDto) {
        commentService.remove(commentId, articleId, userResponseDto.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
