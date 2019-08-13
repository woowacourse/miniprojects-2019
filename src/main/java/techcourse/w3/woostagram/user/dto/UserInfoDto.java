package techcourse.w3.woostagram.user.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    private String email;
    private UserContentsDto userContentsDto;

    @Builder
    public UserInfoDto(String email, UserContentsDto userContentsDto) {
        this.email = email;
        this.userContentsDto = userContentsDto;
    }

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .userContentsDto(UserContentsDto.from(user.getUserContents())).build();
    }
}
