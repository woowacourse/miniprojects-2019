package techcourse.w3.woostagram.article.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String image;

    @Builder
    public Article(String contents, String image) {
        this.contents = contents;
        this.image = image;
    }
}
