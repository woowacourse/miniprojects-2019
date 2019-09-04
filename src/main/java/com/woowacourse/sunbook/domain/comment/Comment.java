package com.woowacourse.sunbook.domain.comment;

import com.woowacourse.sunbook.domain.BaseEntity;
import com.woowacourse.sunbook.domain.Content;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.comment.exception.MismatchAuthException;
import com.woowacourse.sunbook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Comment extends BaseEntity implements Comparable<Comment> {
    private static final String USER_FK_FIELD_NAME = "author_id";
    private static final String USER_FK_NAME = "fk_comment_to_user";
    private static final String ARTICLE_FK_FILED_NAME = "article_id";
    private static final String ARTICLE_FK_NAME = "fk_comment_to_article";

    @Embedded
    private Content content;

    @ManyToOne
    @JoinColumn(name = USER_FK_FIELD_NAME, foreignKey = @ForeignKey(name = USER_FK_NAME))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    @ManyToOne
    @JoinColumn(name = ARTICLE_FK_FILED_NAME, foreignKey = @ForeignKey(name = ARTICLE_FK_NAME))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_comment_to_comment"))
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Comment> children = new ArrayList<>();

    public Comment(final Content content, final User author, final Article article, final Comment parent) {
        this.content = content;
        this.author = author;
        this.article = article;
        this.parent = parent;
    }

    public Comment modify(final Content content, final User user, final Article article) {
        validateAuth(user, article);
        this.content = content;

        return this;
    }

    public void validateAuth(final User user, final Article article) {
        if (author.equals(user) && this.article.equals(article)) {
            return;
        }

        throw new MismatchAuthException();
    }

    @Override
    public int compareTo(Comment comment) {
        return this.getCreatedTime().compareTo(comment.getCreatedTime());
    }
}
