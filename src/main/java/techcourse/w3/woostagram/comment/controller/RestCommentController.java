package techcourse.w3.woostagram.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.comment.dto.CommentDto;
import techcourse.w3.woostagram.comment.service.CommentService;
import techcourse.w3.woostagram.user.support.LoggedInUser;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class RestCommentController {
    private final CommentService commentService;

    public RestCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto, @LoggedInUser String email, @PathVariable Long articleId) {
        return new ResponseEntity<>(commentService.save(commentDto, email, articleId), HttpStatus.CREATED);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<List<CommentDto>> read(@PathVariable Long articleId) {
        return ResponseEntity.ok(commentService.findByArticleId(articleId));
    }
}
