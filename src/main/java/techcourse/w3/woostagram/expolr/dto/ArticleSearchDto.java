package techcourse.w3.woostagram.expolr.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.comment.dto.CommentDto;

import java.util.List;

@Getter
@Setter
public class ArticleSearchDto {
    private ArticleDto article;
    private List<CommentDto> comments;
    private Boolean mine;
    private Long likes;
    private Boolean liking;

    @Builder
    public ArticleSearchDto(ArticleDto article, List<CommentDto> comments, Boolean mine, Long likes, Boolean liking) {
        this.article = article;
        this.comments = comments;
        this.mine = mine;
        this.likes = likes;
        this.liking = liking;
    }

    public static ArticleSearchDto from(Article article, List<CommentDto> comments, Boolean mine, Long likes, Boolean liking) {
        return ArticleSearchDto.builder()
                .article(ArticleDto.from(article))
                .comments(comments)
                .mine(mine)
                .likes(likes)
                .liking(liking)
                .build();
    }
}
