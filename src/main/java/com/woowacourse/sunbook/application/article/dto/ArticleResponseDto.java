package com.woowacourse.sunbook.application.article.dto;

import com.woowacourse.sunbook.domain.article.ArticleFeature;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDto {
    private Long id;
    private ArticleFeature articleFeature;
    private LocalDateTime updatedTime;
}
