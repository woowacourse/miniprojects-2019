package techcourse.w3.woostagram.article.dto;

import lombok.*;
import techcourse.w3.woostagram.article.domain.Article;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String contents;
    private String image;

    @Builder
    public ArticleDto(Long id, String contents, String image) {
        this.id = id;
        this.contents = contents;
        this.image = image;
    }

    public ArticleDto from(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .contents(article.getContents())
                .image(article.getImage())
                .build();
    }

    public Article toArticle() {
        return Article.builder()
                .contents(contents)
                .image(image)
                .build();
    }
}