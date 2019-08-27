package techcourse.w3.woostagram.tag.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import techcourse.w3.woostagram.article.domain.Article;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"article", "tag"})
})
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article")
    private Article article;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tag")
    private Tag tag;

    @Builder
    public HashTag(Article article, Tag tag) {
        this.article = article;
        this.tag = tag;
    }
}
