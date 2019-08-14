package com.woowacourse.zzazanstagram.model.article;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.domain.vo.ImageUrl;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleRequest;

public class ArticleAssembler {
    //Todo author 추가시 변경
    public static Article toEntity(ArticleRequest dto) {
        ImageUrl imageUrl = ImageUrl.of(dto.getImageUrl());
        Contents contents = Contents.of(dto.getContents());

        return Article.from(imageUrl, contents);
    }
}
