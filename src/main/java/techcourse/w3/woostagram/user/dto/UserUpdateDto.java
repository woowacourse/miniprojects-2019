package techcourse.w3.woostagram.user.dto;

import lombok.*;
import techcourse.w3.woostagram.user.domain.UserContents;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserUpdateDto {
    private String name;

    @NotBlank(message = UserContents.BLANK_ERROR_MESSAGE)
    @Pattern(regexp = "[A-Za-z0-9_.]+", message = UserContents.PATTERN_ERROR_MESSAGE)
    @Size(min = 1, max = 29, message = UserContents.LENGTH_ERROR_MESSAGE)
    private String userName;

    private String contents;

    @Builder
    public UserUpdateDto(String name, String userName, String contents) {
        this.name = name;
        this.userName = userName;
        this.contents = contents;
    }

    public UserContents toEntity() {
        return UserContents.builder()
                .userName(userName)
                .name(name)
                .contents(contents)
                .build();
    }
}
