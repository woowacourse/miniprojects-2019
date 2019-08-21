package com.woowacourse.sunbook.mir.domain;

import com.woowacourse.sunbook.domain.BaseEntity;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.mir.domain.exception.MismatchAuthException;
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
    private static final String USER_FK_FIELD_NAME = "writer_id";
    private static final String USER_FK_NAME = "fk_comment_to_user";
    private static final String ARTICLE_FK_FILED_NAME = "article_id";
    private static final String ARTICLE_FK_NAME = "fk_comment_to_article";

    @Embedded
    private CommentFeature commentFeature;

    @ManyToOne
    @JoinColumn(name = USER_FK_FIELD_NAME, foreignKey = @ForeignKey(name = USER_FK_NAME))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User writer;

    @ManyToOne
    @JoinColumn(name = ARTICLE_FK_FILED_NAME, foreignKey = @ForeignKey(name = ARTICLE_FK_NAME))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    public Comment(final CommentFeature commentFeature, final User writer, final Article article) {
        this.commentFeature = commentFeature;
        this.writer = writer;
        this.article = article;
    }

    public Comment modify(final CommentFeature commentFeature, final User user, final Article article) {
        validateAuth(user, article);
        this.commentFeature = commentFeature;

        return this;
    }

    public void validateAuth(User user, Article article) {
        if (writer.equals(user) && this.article.equals(article)) {
            return;
        }
        throw new MismatchAuthException();
    }
}
