package com.wootecobook.turkey.comment.controller;

import com.wootecobook.turkey.comment.service.CommentService;
import com.wootecobook.turkey.comment.service.dto.CommentCreate;
import com.wootecobook.turkey.comment.service.dto.CommentResponse;
import com.wootecobook.turkey.comment.service.dto.CommentUpdate;
import com.wootecobook.turkey.commons.GoodResponse;
import com.wootecobook.turkey.commons.resolver.LoginUser;
import com.wootecobook.turkey.commons.resolver.UserSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentApiController {

    private final CommentService commentService;

    public CommentApiController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponse>> list(@PageableDefault(size = 5, sort = "createdAt") Pageable pageable,
                                                      @PathVariable Long postId) {

        final Page<CommentResponse> commentResponses = commentService.findCommentResponsesByPostId(postId, pageable);
        return ResponseEntity.ok(commentResponses);
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<Page<CommentResponse>> childCommentList(@PageableDefault(size = 5, sort = "createdAt") Pageable pageable,
                                                                  @PathVariable Long id) {

        final Page<CommentResponse> commentResponses = commentService.findCommentResponsesByParentId(id, pageable);
        return ResponseEntity.ok(commentResponses);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody CommentCreate commentCreate,
                                                  @PathVariable Long postId,
                                                  @LoginUser UserSession userSession) {

        final CommentResponse commentResponse = commentService.save(commentCreate, userSession.getId(), postId);
        final URI uri = linkTo(CommentApiController.class, postId).toUri();
        return ResponseEntity.created(uri).body(commentResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@RequestBody CommentUpdate commentUpdate,
                                                  @PathVariable Long id,
                                                  @LoginUser UserSession userSession) {

        CommentResponse commentResponse = commentService.update(commentUpdate, id, userSession.getId());
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id,
                                 @PathVariable Long postId,
                                 @LoginUser UserSession userSession) {

        commentService.delete(id, userSession.getId());
        final URI uri = linkTo(CommentApiController.class, postId).toUri();
        return ResponseEntity.noContent().location(uri).build();
    }

    @GetMapping("/{id}/good")
    public ResponseEntity<GoodResponse> toggleGood(@PathVariable Long id, @LoginUser UserSession userSession) {
        GoodResponse goodResponse = commentService.toggleGood(id, userSession.getId());

        return ResponseEntity.ok(goodResponse);
    }

    @GetMapping("/{id}/good/count")
    public ResponseEntity<GoodResponse> countGood(@PathVariable Long id, @LoginUser UserSession userSession) {
        GoodResponse goodResponse = commentService.countGoodResponseByComment(id, userSession.getId());

        return ResponseEntity.ok(goodResponse);
    }
}

