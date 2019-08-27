package com.woowacourse.sunbook.domain.reaction;

import com.woowacourse.sunbook.domain.BaseEntity;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class ReactionArticle extends BaseEntity {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    private Boolean hasGood;

    public ReactionArticle(final User author, final Article article) {
        this.author = author;
        this.article = article;
        this.hasGood = false;
    }

    public void toggleGood() {
        this.hasGood = !(this.hasGood);
    }

    public void addGood() {
        if (this.hasGood) {
            throw new IllegalReactionException();
        }

        this.hasGood = true;
    }

    public void removeGood() {
        if (this.hasGood) {
            this.hasGood = false;

            return;
        }

        throw new IllegalReactionException();
    }
}
