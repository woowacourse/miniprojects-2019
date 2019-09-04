package techcourse.w3.woostagram.user.dto;

import lombok.*;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserContents;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserCreateDto {
    @NotBlank(message = User.ERROR_EMAIL)
    @Email(message = User.ERROR_EMAIL)
    private String email;

    private String name;

    @NotBlank(message = UserContents.BLANK_ERROR_MESSAGE)
    @Pattern(regexp = "[A-Za-z0-9_.]+", message = UserContents.PATTERN_ERROR_MESSAGE)
    @Size(min = 1, max = 29, message = UserContents.LENGTH_ERROR_MESSAGE)
    private String userName;

    @NotBlank(message = User.ERROR_PASSWORD)
    @Pattern(regexp = User.PATTERN_PASSWORD, message = User.ERROR_PASSWORD)
    private String password;

    @Builder
    public UserCreateDto(String email, String name, String userName, String password) {
        this.email = email;
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .userContents(UserContents.builder()
                        .name(name)
                        .userName(userName)
                        .build())
                .password(password)
                .build();
    }
}
