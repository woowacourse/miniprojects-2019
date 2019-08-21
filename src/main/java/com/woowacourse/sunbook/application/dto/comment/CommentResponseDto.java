package com.woowacourse.sunbook.application.dto.comment;

import com.woowacourse.sunbook.domain.comment.CommentFeature;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentResponseDto {
    private Long id;
    private CommentFeature commentFeature;
    private String writerName;
    private LocalDateTime updatedTime;
}
