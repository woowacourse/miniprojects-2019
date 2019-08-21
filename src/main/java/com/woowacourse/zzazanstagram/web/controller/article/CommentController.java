package com.woowacourse.zzazanstagram.web.controller.article;

import com.woowacourse.zzazanstagram.model.comment.domain.vo.CommentContents;
import com.woowacourse.zzazanstagram.model.comment.dto.CommentResponse;
import com.woowacourse.zzazanstagram.model.comment.service.CommentService;
import com.woowacourse.zzazanstagram.model.member.MemberSession;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{articleId}/comments/new")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long articleId, @Valid @RequestBody CommentContents commentContents,
                                                         MemberSession memberSession) {
        CommentResponse commentResponse = commentService.save(commentContents, articleId, memberSession.getEmail());
        return ResponseEntity.ok(commentResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> temp(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError allError : allErrors) {
            errorMessage.append(allError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errorMessage.toString());
    }

}
