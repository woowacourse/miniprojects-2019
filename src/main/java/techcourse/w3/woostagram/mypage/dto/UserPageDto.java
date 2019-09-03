package techcourse.w3.woostagram.mypage.dto;

import lombok.*;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserPageDto {
    private UserInfoDto userInfoDto;
    private boolean mine;
    private boolean following;

    @Builder
    public UserPageDto(UserInfoDto userInfoDto, boolean mine, boolean following) {
        this.userInfoDto = userInfoDto;
        this.mine = mine;
        this.following = following;
    }

    public static UserPageDto from(UserInfoDto pageUser, UserInfoDto loginUser, boolean following) {
        return UserPageDto.builder()
                .userInfoDto(pageUser)
                .mine(loginUser.equals(pageUser))
                .following(following)
                .build();
    }
}
