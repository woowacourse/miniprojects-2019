package techcourse.w3.woostagram.user.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode
@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    public static final String ERROR_EMAIL = "올바른 email 형식이 아닙니다.";
    public static final String ERROR_PASSWORD = "올바른 비밀번호 형식이 아닙니다.";
    public static final String PATTERN_PASSWORD = ".*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = ERROR_EMAIL)
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = PATTERN_PASSWORD, message = ERROR_PASSWORD)
    @Column(nullable = false)
    private String password;

    @Embedded
    private UserContents userContents;

    @Builder
    public User(Long id, String email, String password, UserContents userContents) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userContents = userContents;
    }

    public void contentsUpdated(UserContents userContents) {

        this.userContents = userContents;
    }
}
