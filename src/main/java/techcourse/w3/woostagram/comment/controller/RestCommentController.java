package techcourse.w3.woostagram.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.comment.dto.CommentDto;
import techcourse.w3.woostagram.comment.service.CommentService;
import techcourse.w3.woostagram.user.support.LoggedInUser;

@RestController
@RequestMapping("/api/comments")
public class RestCommentController {
    private final CommentService commentService;

    public RestCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto, @LoggedInUser String email) {
        return ResponseEntity.ok(commentService.save(commentDto, email));
    }
}
