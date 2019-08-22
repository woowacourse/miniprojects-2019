package techcourse.w3.woostagram.user.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserProfileImageDto {
    private String originalImageFile;
    private MultipartFile imageFile;

    @Builder
    public UserProfileImageDto(String originalImageFile, MultipartFile imageFile) {
        this.originalImageFile = originalImageFile;
        this.imageFile = imageFile;
    }
}
