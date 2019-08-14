package techcourse.w3.woostagram.article.assembler;

import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

public class ArticleAssembler {
    public static Article toArticle(ArticleDto articleDto, String fullPath, User user) {
        return Article.builder()
                .contents(articleDto.getContents())
                .imageUrl(fullPath)
                .user(user)
                .build();
    }

    public static ArticleDto toArticleDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .contents(article.getContents())
                .imageUrl(article.getImageUrl())
                .userInfoDto(UserInfoDto.from(article.getUser()))
                .build();
    }
}
