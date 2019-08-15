package com.wootecobook.turkey.comment.service.dto;


import com.wootecobook.turkey.comment.domain.Comment;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private Long id;
    private String contents;
    private CommentResponse parent;
    private UserResponse userResponse;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private CommentResponse(final Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.parent = CommentResponse.from(comment.getParent());
        this.userResponse = UserResponse.from(comment.getUser());
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment);
    }

}
