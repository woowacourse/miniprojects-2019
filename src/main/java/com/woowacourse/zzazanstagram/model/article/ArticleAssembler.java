package com.woowacourse.zzazanstagram.model.article;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Image;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleRequest;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleResponse;
import com.woowacourse.zzazanstagram.model.member.Member;

import java.time.LocalDateTime;

public class ArticleAssembler {
    public static Article toEntity(ArticleRequest dto, Member author) {
        Image image = Image.of(dto.getImageUrl());
        Contents contents = Contents.of(dto.getContents());

        return Article.from(image, contents, author);
    }

    public static ArticleResponse toDto(Article article) {
        Long id = article.getId();
        Image image = article.getImage();
        Contents contents = article.getContents();
        LocalDateTime createdDate = article.getCreatedDate();
        LocalDateTime lastModifiedDate = article.getLastModifiedDate();

        return new ArticleResponse(id, image.getUrl(), contents.getContents(), createdDate, lastModifiedDate);
    }
}
