package com.woowacourse.sunbook.application.dto.article;

import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.user.UserName;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDto {
    private Long id;
    private ArticleFeature articleFeature;
    private LocalDateTime updatedTime;
    private UserName authorName;
}
