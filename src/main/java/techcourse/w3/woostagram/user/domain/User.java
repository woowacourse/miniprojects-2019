package techcourse.w3.woostagram.user.domain;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode
@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

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
