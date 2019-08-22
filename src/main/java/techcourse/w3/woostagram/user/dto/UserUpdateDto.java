package techcourse.w3.woostagram.user.dto;

import lombok.*;
import techcourse.w3.woostagram.user.domain.UserContents;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserUpdateDto {
    private String name;
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
