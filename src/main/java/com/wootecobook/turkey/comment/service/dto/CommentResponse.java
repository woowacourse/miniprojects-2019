package com.wootecobook.turkey.comment.service.dto;


import com.wootecobook.turkey.comment.domain.Comment;
import com.wootecobook.turkey.good.service.dto.GoodResponse;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private Long parentId;
    private String contents;
    private int countOfChildren;
    private UserResponse userResponse;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GoodResponse goodResponse;

    @Builder
    private CommentResponse(final Long id, final Long parentId, final String contents, final int countOfChildren, final UserResponse userResponse, final LocalDateTime createdAt, final LocalDateTime updatedAt, final GoodResponse goodResponse) {
        this.id = id;
        this.parentId = parentId;
        this.contents = contents;
        this.countOfChildren = countOfChildren;
        this.userResponse = userResponse;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.goodResponse = goodResponse;
    }

    public static CommentResponse from(final Comment comment) {
        return from(comment, GoodResponse.init());
    }

    public static CommentResponse from(final Comment comment, final GoodResponse goodResponse) {
        return CommentResponse.builder()
                .id(comment.getId())
                .contents(comment.getContents())
                .parentId(comment.getParentCommentId().orElse(null))
                .countOfChildren(comment.getCountOfChildren())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .userResponse(UserResponse.from(comment.getAuthor()))
                .goodResponse(goodResponse)
                .build();
    }
}
