package com.woowacourse.zzazanstagram.model.article;

import com.woowacourse.zzazanstagram.model.article.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.vo.ImageUrl;
import com.woowacourse.zzazanstagram.model.common.BaseEntity;
import com.woowacourse.zzazanstagram.model.member.Member;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Article extends BaseEntity {
    private final ImageUrl imageUrl;
    private final Contents contents;

    @Column(name = "author", nullable = false)
    private Member author;

    public Article(final ImageUrl imageUrl, final Contents contents, final Member author) {
        this.imageUrl = imageUrl;
        this.contents = contents;
        this.author = author;
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public Contents getContents() {
        return contents;
    }

    public Member getAuthor() {
        return author;
    }
}