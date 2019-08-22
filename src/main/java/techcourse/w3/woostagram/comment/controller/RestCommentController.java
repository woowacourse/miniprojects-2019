package techcourse.w3.woostagram.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techcourse.w3.woostagram.comment.dto.CommentDto;
import techcourse.w3.woostagram.comment.service.CommentService;
import techcourse.w3.woostagram.common.support.LoggedInUser;

import java.util.List;

@RestController
@RequestMapping("/api/comments/{articleId}")
public class RestCommentController {
    private final CommentService commentService;

    public RestCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto, @LoggedInUser String email, @PathVariable Long articleId) {
        return new ResponseEntity<>(commentService.save(commentDto, email, articleId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> read(@PathVariable Long articleId, @LoggedInUser String email) {
        return ResponseEntity.ok(commentService.findByArticleId(articleId, email));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long commentId, @LoggedInUser String email) {
        commentService.deleteById(commentId, email);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
