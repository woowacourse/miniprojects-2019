package techcourse.w3.woostagram.user.dto;

import lombok.*;
import techcourse.w3.woostagram.user.domain.UserContents;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserContentsDto {
    private String name;
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
