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
public class UserDto {
    private String email;
    private String password;

    @Builder
    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .userContents(UserContents.builder()
                        .userName(email).build())
                .build();
    }
}
