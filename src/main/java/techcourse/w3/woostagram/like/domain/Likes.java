package techcourse.w3.woostagram.like.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.common.domain.BaseEntity;
import techcourse.w3.woostagram.user.domain.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"article", "user"})
})
public class Likes extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article")
    private Article article;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user")
    private User user;

    @Builder
    public Likes(Article article, User user) {
        this.article = article;
        this.user = user;
    }
}
