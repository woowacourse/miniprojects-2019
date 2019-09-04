package techcourse.w3.woostagram.user.dto;

import lombok.*;
import techcourse.w3.woostagram.user.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto {
    private static final String DEFAULT_USER_NAME = "default";

    @Email(message = User.ERROR_EMAIL)
    private String email;

    @Pattern(regexp = User.PATTERN_PASSWORD, message = User.ERROR_PASSWORD)
    private String password;

    @Builder
    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
