package techcourse.w3.woostagram.like.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.user.domain.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User likeUser;

    @Builder
    public Likes(Article article, User likeUser) {
        this.article = article;
        this.likeUser = likeUser;
    }

    public void nullify() {
        article = null;
        likeUser = null;
    }
}
