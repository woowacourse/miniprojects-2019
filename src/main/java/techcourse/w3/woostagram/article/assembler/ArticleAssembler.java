package techcourse.w3.woostagram.article.assembler;

import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.dto.ArticleDto;

public class ArticleAssembler {
    public static Article toArticle(ArticleDto articleDto, String fullPath) {
        return Article.builder()
                .contents(articleDto.getContents())
                .imageUrl(fullPath)
                .build();
    }

    public static ArticleDto toArticleDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .contents(article.getContents())
                .imageUrl(article.getImageUrl())
                .build();
    }
}
