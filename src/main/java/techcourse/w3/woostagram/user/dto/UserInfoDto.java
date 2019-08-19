package techcourse.w3.woostagram.user.dto;


import lombok.*;
import techcourse.w3.woostagram.user.domain.User;

@ToString
@EqualsAndHashCode
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
