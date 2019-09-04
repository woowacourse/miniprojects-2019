package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.dto.CommentRequestDto;
import com.woowacourse.edd.application.response.CommentResponse;
import com.woowacourse.edd.application.response.SessionUser;
import com.woowacourse.edd.application.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.woowacourse.edd.presentation.controller.CommentController.COMMENT_URL;
import static com.woowacourse.edd.presentation.controller.VideoController.VIDEO_URL;

@RestController
@RequestMapping(COMMENT_URL)
public class CommentController {

    public static final String COMMENT_URL = VIDEO_URL + "/{videoId}/comments";
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity save(@PathVariable Long videoId, @RequestBody @Valid CommentRequestDto commentRequestDto, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        CommentResponse commentResponse = commentService.save(videoId, sessionUser.getId(), commentRequestDto);
        return ResponseEntity.created(URI.create(VIDEO_URL + "/" + videoId + "/comments/" + commentResponse.getId())).body(commentResponse);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> retrieve(@PathVariable Long videoId) {
        List<CommentResponse> commentResponses = commentService.retrieve(videoId);
        return ResponseEntity.ok(commentResponses);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> update(@PathVariable Long videoId, @PathVariable Long commentId,
                                                  @RequestBody @Valid CommentRequestDto commentRequestDto, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        CommentResponse commentResponse = commentService.update(commentId, sessionUser.getId(), videoId, commentRequestDto);
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity delete(@PathVariable Long videoId, @PathVariable Long commentId,
                                 HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        commentService.delete(commentId, sessionUser.getId(), videoId);
        return ResponseEntity.noContent().build();
    }
}
