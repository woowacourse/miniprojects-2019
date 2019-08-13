package techcourse.w3.woostagram.user.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode
@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]{8,}$")
    @Column(nullable = false)
    private String password;

    @Embedded
    private UserContents userContents;

    @Builder
    public User(String email, String password, UserContents userContents) {
        this.email = email;
        this.password = password;
        this.userContents = userContents;
    }
}
