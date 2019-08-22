package techcourse.w3.woostagram.user.dto;

import lombok.*;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto {
    @Email(message = User.ERROR_EMAIL)
    private String email;

    @Pattern(regexp = User.PATTERN_PASSWORD, message = User.ERROR_PASSWORD)
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
