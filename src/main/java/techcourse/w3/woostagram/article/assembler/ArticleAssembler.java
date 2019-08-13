package techcourse.w3.woostagram.article.assembler;

import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.dto.ArticleDto;

public class ArticleAssembler {
    public static Article toArticle(ArticleDto articleDto, String uploadPath) {
        return Article.builder()
                .contents(articleDto.getContents())
                .imageUrl(uploadPath + articleDto.getImageFile().getOriginalFilename())
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
