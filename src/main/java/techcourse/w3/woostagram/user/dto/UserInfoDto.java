package techcourse.w3.woostagram.user.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.w3.woostagram.user.domain.User;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String email;
    private UserContentsDto userContentsDto;

    @Builder
    public UserInfoDto(Long id, String email, UserContentsDto userContentsDto) {
        this.id = id;
        this.email = email;
        this.userContentsDto = userContentsDto;
    }

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .userContentsDto(UserContentsDto.from(user.getUserContents())).build();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .email(email)
                .userContents(userContentsDto.toEntity())
                .build();
    }
}
