package techcourse.w3.woostagram.article.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import techcourse.w3.woostagram.user.domain.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String contents;
    @Column(nullable = false, length = 1000)
    private String imageUrl;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_ARTICLE_TO_USER"), name = "user")
    private User user;

    @Builder
    public Article(String contents, String imageUrl, User user) {
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public void update(String contents) {
        this.contents = contents;
    }
}
