package com.woowacourse.zzazanstagram.model.article.domain;

import com.woowacourse.zzazanstagram.model.article.domain.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.domain.vo.ImageUrl;
import com.woowacourse.zzazanstagram.model.common.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Article extends BaseEntity {
    private ImageUrl imageUrl;
    private Contents contents;

//    @Column(name = "author", nullable = false)
//    private Member author;

    private Article() {
    }

    private Article(ImageUrl imageUrl, Contents contents) {
        this.imageUrl = imageUrl;
        this.contents = contents;
    }

/*    public Article(final ImageUrl imageUrl, final Contents contents, final Member author) {
        this.imageUrl = imageUrl;
        this.contents = contents;
        this.author = author;
    }*/


    public static Article from(final ImageUrl imageUrl, final Contents contents) {
        return new Article(imageUrl, contents);
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public Contents getContents() {
        return contents;
    }

/*    public Member getAuthor() {
        return author;
    }*/
}