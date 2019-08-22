package techcourse.w3.woostagram.comment.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.common.domain.BaseEntity;
import techcourse.w3.woostagram.user.domain.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contents;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_COMMENT_TO_USER"))
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_COMMENT_TO_ARTICLE"))
    private Article article;

    @Builder
    public Comment(String contents, User user, Article article) {
        this.contents = contents;
        this.user = user;
        this.article = article;
    }

    public boolean isAuthor(long loggedInUserId) {
        return loggedInUserId == user.getId();
    }
}
