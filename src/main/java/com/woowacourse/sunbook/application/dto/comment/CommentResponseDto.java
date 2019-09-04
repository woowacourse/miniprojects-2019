package com.woowacourse.sunbook.application.dto.comment;

import com.woowacourse.sunbook.domain.Content;
import com.woowacourse.sunbook.domain.user.UserName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentResponseDto {
    private Long id;
    private Content content;
    private UserName authorName;
    private LocalDateTime updatedTime;
}
