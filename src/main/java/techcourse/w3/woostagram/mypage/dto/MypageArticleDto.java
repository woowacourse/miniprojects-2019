package techcourse.w3.woostagram.mypage.dto;

import lombok.*;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.dto.ArticleDto;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MypageArticleDto {
    private ArticleDto article;
    private Long likes;
    private Long commentNum;

    @Builder
    public MypageArticleDto(ArticleDto article, Long likes, Long commentNum) {
        this.article = article;
        this.likes = likes;
        this.commentNum = commentNum;
    }

    public static MypageArticleDto from(Article article, Long likes, Long commentNum) {
        return MypageArticleDto.builder()
                .article(ArticleDto.from(article))
                .likes(likes)
                .commentNum(commentNum)
                .build();
    }
}
