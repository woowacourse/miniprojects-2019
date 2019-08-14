package com.wootecobook.turkey.comment.service.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.wootecobook.turkey.comment.domain.Comment;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentResponse {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Long id;
    private String contents;
    private CommentResponse parent;
    private UserResponse userResponse;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
