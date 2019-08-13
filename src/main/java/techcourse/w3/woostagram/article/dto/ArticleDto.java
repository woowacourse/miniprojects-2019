package techcourse.w3.woostagram.article.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String contents;
    private MultipartFile imageFile;
    private String imageUrl;

    @Builder
    public ArticleDto(Long id, String contents, MultipartFile imageFile, String imageUrl) {
        this.id = id;
        this.contents = contents;
        this.imageFile = imageFile;
        this.imageUrl = imageUrl;
    }
}