package techcourse.w3.woostagram.article.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String contents;
    private MultipartFile imageFile;
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
}