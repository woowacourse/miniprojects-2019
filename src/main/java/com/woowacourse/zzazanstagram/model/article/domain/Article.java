package com.woowacourse.zzazanstagram.model.article.domain;

import com.woowacourse.zzazanstagram.model.article.domain.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Image;
import com.woowacourse.zzazanstagram.model.common.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Article extends BaseEntity {
    private Image image;
    private Contents contents;

//    @Column(name = "author", nullable = false)
//    private Member author;

    private Article() {
    }

    private Article(Image image, Contents contents) {
        this.image = image;
        this.contents = contents;
    }

/*    public Article(final Image image, final Contents contents, final Member author) {
        this.image = image;
        this.contents = contents;
        this.author = author;
    }*/


    public static Article from(final Image image, final Contents contents) {
        return new Article(image, contents);
    }

    public Image getImage() {
        return image;
    }

    public Contents getContents() {
        return contents;
    }

/*    public Member getAuthor() {
        return author;
    }*/
}