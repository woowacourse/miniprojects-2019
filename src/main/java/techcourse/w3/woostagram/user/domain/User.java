package techcourse.w3.woostagram.user.domain;

import lombok.*;
import techcourse.w3.woostagram.common.domain.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
public class User extends BaseEntity {
    private static final String DEFAULT_PROFILE_IMAGE
            = "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/default_profile_image.jpg";
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

    @Column(length = 1000)
    private String profile;

    @Builder
    public User(Long id, String email, String password, UserContents userContents, String profile) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userContents = userContents;
        this.profile = profile;
    }

    public void updateContents(UserContents userContents) {
        this.userContents = userContents;
    }

    public void updateProfile(String profile) {
        this.profile = profile;
    }

    public String makeProfileDefault() {
        this.profile = DEFAULT_PROFILE_IMAGE;
        return this.profile;
    }

    public boolean hasDefaultImage() {
        return this.profile.equals(DEFAULT_PROFILE_IMAGE);
    }

    public boolean isSameUserName(String userName) {
        return this.userContents.isSameUserName(userName);
    }

    @PrePersist
    public void prePersist() {
        this.profile = Objects.isNull(this.profile) ? DEFAULT_PROFILE_IMAGE : this.profile;
    }
}
