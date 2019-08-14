package techcourse.w3.woostagram.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.w3.woostagram.user.domain.UserContents;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserContentsDto {
    private String name;

    @NotBlank(message = "빈칸을 허용하지 않는 항목입니다.")
    private String userName;

    private String contents;
    private String profile;

    @Builder
    public UserContentsDto(String name, String userName, String contents, String profile) {
        this.name = name;
        this.userName = userName;
        this.contents = contents;
        this.profile = profile;
    }

    public static UserContentsDto from(UserContents userContents) {
        return UserContentsDto.builder()
                .name(userContents.getName())
                .userName(userContents.getUserName())
                .contents(userContents.getContents())
                .profile(userContents.getProfile()).build();
    }

    public UserContents toEntity() {
        return UserContents.builder()
                .userName(userName)
                .name(name)
                .contents(contents)
                .profile(profile)
                .build();
    }
}
