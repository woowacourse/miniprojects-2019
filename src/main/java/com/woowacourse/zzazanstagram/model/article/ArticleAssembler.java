package com.woowacourse.zzazanstagram.model.article;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.domain.vo.ImageUrl;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleRequest;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleResponse;

import java.time.LocalDateTime;

//Todo author 추가시 변경
public class ArticleAssembler {
    public static Article toEntity(ArticleRequest dto) {
        ImageUrl imageUrl = ImageUrl.of(dto.getImageUrl());
        Contents contents = Contents.of(dto.getContents());

        return Article.from(imageUrl, contents);
    }

    public static ArticleResponse toDto(Article article) {
        Long id = article.getId();
        ImageUrl imageUrl = article.getImageUrl();
        Contents contents = article.getContents();
        LocalDateTime createdDate = article.getCreatedDate();
        LocalDateTime lastModifiedDate = article.getLastModifiedDate();

        return new ArticleResponse(id, imageUrl.getUrl(), contents.getContents(), createdDate, lastModifiedDate);
    }
}
