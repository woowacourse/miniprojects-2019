package com.woowacourse.sunbook.mir.application.dto;

import com.woowacourse.sunbook.mir.domain.CommentFeature;
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
