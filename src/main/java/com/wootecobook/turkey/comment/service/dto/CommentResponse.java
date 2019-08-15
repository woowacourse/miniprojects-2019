package com.wootecobook.turkey.comment.service.dto;


import com.wootecobook.turkey.comment.domain.Comment;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String contents;
    private CommentResponse parent;
    private UserResponse userResponse;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private CommentResponse(final Long id, final String contents, final CommentResponse parent,
                            final UserResponse userResponse, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.contents = contents;
        this.parent = parent;
        this.userResponse = userResponse;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponse from(Comment comment) {
        return comment == null ? null : CommentResponse.builder()
                .id(comment.getId())
                .contents(comment.getContents())
                .userResponse(UserResponse.from(comment.getUser()))
                .parent(CommentResponse.from(comment.getParent()))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

}
