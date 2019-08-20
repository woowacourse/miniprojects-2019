package techcourse.w3.woostagram.user.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.user.domain.UserContents;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserUpdateDto {
    private String name;
    private String userName;
    private String contents;
    private String originalImageFile;
    private MultipartFile imageFile;

    @Builder
    public UserUpdateDto(String name, String userName, String contents, String originalImageFile, MultipartFile imageFile) {
        this.name = name;
        this.userName = userName;
        this.contents = contents;
        this.originalImageFile = originalImageFile;
        this.imageFile = imageFile;
    }

    public UserContents toEntity(String fullPath) {
        return UserContents.builder()
                .userName(userName)
                .name(name)
                .contents(contents)
                .profile(fullPath)
                .build();
    }
}
