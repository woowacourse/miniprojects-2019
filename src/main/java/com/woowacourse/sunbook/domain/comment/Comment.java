package com.woowacourse.sunbook.domain.comment;

import com.woowacourse.sunbook.domain.BaseEntity;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.comment.exception.MismatchAuthException;
import com.woowacourse.sunbook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Comment extends BaseEntity {
    private static final String USER_FK_FIELD_NAME = "author_id";
    private static final String USER_FK_NAME = "fk_comment_to_user";
    private static final String ARTICLE_FK_FILED_NAME = "article_id";
    private static final String ARTICLE_FK_NAME = "fk_comment_to_article";

    @Embedded
    private CommentFeature commentFeature;

    @ManyToOne
    @JoinColumn(name = USER_FK_FIELD_NAME, foreignKey = @ForeignKey(name = USER_FK_NAME))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    @ManyToOne
    @JoinColumn(name = ARTICLE_FK_FILED_NAME, foreignKey = @ForeignKey(name = ARTICLE_FK_NAME))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    public Comment(final CommentFeature commentFeature, final User author, final Article article) {
        this.commentFeature = commentFeature;
        this.author = author;
        this.article = article;
    }

    public Comment modify(final CommentFeature commentFeature, final User user, final Article article) {
        validateAuth(user, article);
        this.commentFeature = commentFeature;

        return this;
    }

    public void validateAuth(final User user, final Article article) {
        if (author.equals(user) && this.article.equals(article)) {
            return;
        }

        throw new MismatchAuthException();
    }
}
