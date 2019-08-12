package techcourse.w3.woostagram.user.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@EqualsAndHashCode
@ToString
@Getter
@Embeddable
@NoArgsConstructor
public class UserContents {

    @Column(nullable = false, unique = true)
    private String userName;

    private String name;

    @Lob
    private String contents;

    private String profile;

    @Builder
    public UserContents(String userName, String name, String contents, String profile) {
        this.userName = userName;
        this.name = name;
        this.contents = contents;
        this.profile = profile;
    }
}
