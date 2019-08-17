package com.woowacourse.sunbook.application.dto.article;

import com.woowacourse.sunbook.domain.article.ArticleFeature;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ArticleResponseDto {
    private Long id;
    private ArticleFeature articleFeature;
    private LocalDateTime updatedTime;
}
