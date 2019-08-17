package com.woowacourse.sunbook.application.dto.article;

import com.woowacourse.sunbook.domain.article.ArticleFeature;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    private long id;
    private ArticleFeature articleFeature;
    private LocalDateTime updatedTime;

    public ArticleResponseDto(long id, ArticleFeature articleFeature, LocalDateTime updatedTime) {
        this.id = id;
        this.articleFeature = articleFeature;
        this.updatedTime = updatedTime;
    }
}
