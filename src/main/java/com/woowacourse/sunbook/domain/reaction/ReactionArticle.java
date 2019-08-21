package com.woowacourse.sunbook.domain.reaction;

import com.woowacourse.sunbook.domain.BaseEntity;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class ReactionArticle extends BaseEntity {

    @ManyToOne
    private Article article;

    @ManyToOne
    private User author;

    private Boolean hasGood;

    public ReactionArticle(final User author, final Article article) {
        this.author = author;
        this.article = article;
        this.hasGood = false;
    }

    public void addGood() {
        this.hasGood = true;
    }
}
