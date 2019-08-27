package com.woowacourse.sunbook.application.dto.article;

import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.article.OpenRange;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleRequestDto {
    private ArticleFeature articleFeature;
    private OpenRange openRange;

    public ArticleRequestDto(ArticleFeature articleFeature, OpenRange openRange) {
        this.articleFeature = articleFeature;
        this.openRange = openRange;
    }
}
