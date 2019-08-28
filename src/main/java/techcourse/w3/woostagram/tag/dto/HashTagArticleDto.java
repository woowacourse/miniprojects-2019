package techcourse.w3.woostagram.tag.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.dto.ArticleDto;

@NoArgsConstructor
@Getter
@Setter
public class HashTagArticleDto {
    private ArticleDto article;
    private Long likes;
    private Long commentNum;

    @Builder
    public HashTagArticleDto(ArticleDto article, Long likes, Long commentNum) {
        this.article = article;
        this.likes = likes;
        this.commentNum = commentNum;
    }

    public static HashTagArticleDto from(Article article, Long likes, Long commentNum) {
        return HashTagArticleDto.builder()
                .article(ArticleDto.from(article))
                .likes(likes)
                .commentNum(commentNum)
                .build();
    }
}
