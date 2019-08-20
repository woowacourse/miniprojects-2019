package com.woowacourse.dsgram.domain;

import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 2, max = 30)
    @Column(unique = true)
    private String nickName;

    @Size(min = 2, max = 30)
    @Column(nullable = false)
    private String userName;

    @Size(min = 4, max = 30)
    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String webSite;

    @Lob
    private String intro;

    @Column(columnDefinition = "boolean default false")
    private boolean isOAuthUser;

    @Builder
    public User(String email, String nickName, String userName, String password, String webSite, String intro, boolean isOAuthUser) {
        this.email = email;
        this.nickName = nickName;
        this.userName = userName;
        this.password = password;
        this.webSite = webSite;
        this.intro = intro;
        this.isOAuthUser = isOAuthUser;
    }

    public void update(User updatedUser, String email) {
        checkEmail(email);
        this.intro = updatedUser.intro;
        this.userName = updatedUser.userName;
        this.nickName = updatedUser.nickName;
        this.password = updatedUser.password;
        this.webSite = updatedUser.webSite;
    }

    public void changeToOAuthUser() {
        if (!this.isOAuthUser) {
            this.isOAuthUser = true;
        }
    }

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new InvalidUserException("회원정보가 일치하지 않습니다.");
        }
    }

    public void checkEmail(String email) {
        if (!this.email.equals(email)) {
            throw new InvalidUserException("회원정보가 일치하지 않습니다.");
        }
    }

    public boolean equalsNickName(String nickName) {
        return this.nickName.equals(nickName);
    }
}
