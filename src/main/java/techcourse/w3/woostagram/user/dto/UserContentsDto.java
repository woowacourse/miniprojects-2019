package techcourse.w3.woostagram.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.w3.woostagram.user.domain.UserContents;

@Getter
@Setter
@NoArgsConstructor
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
}
