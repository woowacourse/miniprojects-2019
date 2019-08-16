package techcourse.w3.woostagram.article.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class ArticleDto {
    private MultipartFile imageFile;
    private Long id;
    private String contents;
    private String imageUrl;
    private UserInfoDto userInfoDto;

    @Builder
    public ArticleDto(Long id, String contents, MultipartFile imageFile, String imageUrl, UserInfoDto userInfoDto) {
        this.id = id;
        this.contents = contents;
        this.imageFile = imageFile;
        this.imageUrl = imageUrl;
        this.userInfoDto = userInfoDto;
    }

    public static ArticleDto from(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .contents(article.getContents())
                .imageUrl(article.getImageUrl())
                .userInfoDto(UserInfoDto.from(article.getUser()))
                .build();
    }

    public Article toEntity(String fullPath, User user) {
        return Article.builder()
                .contents(contents)
                .imageUrl(fullPath)
                .user(user)
                .build();
    }
}