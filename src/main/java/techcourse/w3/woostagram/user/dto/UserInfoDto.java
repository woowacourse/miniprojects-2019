package techcourse.w3.woostagram.user.dto;


import lombok.*;
import techcourse.w3.woostagram.user.domain.User;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class UserInfoDto {
    private Long id;
    private String email;
    private UserContentsDto userContentsDto;
    private String profile;

    @Builder
    public UserInfoDto(Long id, String email, UserContentsDto userContentsDto, String profile) {
        this.id = id;
        this.email = email;
        this.userContentsDto = userContentsDto;
        this.profile = profile;
    }

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userContentsDto(UserContentsDto.from(user.getUserContents()))
                .profile(user.getProfile())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .email(email)
                .userContents(userContentsDto.toEntity())
                .profile(profile)
                .build();
    }
}
