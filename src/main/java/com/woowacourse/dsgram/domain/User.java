package com.woowacourse.dsgram.domain;

import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 2, max = 10)
    @Column(nullable = false, unique = true)
    private String nickName;

    @Size(min = 2, max = 10)
    @Column(nullable = false)
    private String userName;

    @Size(min = 4, max = 16)
    @Column(nullable = false)
    private String password;

    @Column
    private String webSite;

    @Lob
    private String intro;

    @Builder
    public User(@Email String email, @Size(min = 2, max = 10) String nickName, @Size(min = 2, max = 10) String userName, @Size(min = 4, max = 16) String password, String webSite, String intro) {
        this.email = email;
        this.nickName = nickName;
        this.userName = userName;
        this.password = password;
        this.webSite = webSite;
        this.intro = intro;
    }

    public void update(User updatedUser, String email) {
        checkEmail(email);
        this.intro = updatedUser.intro;
        this.userName = updatedUser.userName;
        this.nickName = updatedUser.nickName;
        this.password = updatedUser.password;
        this.webSite = updatedUser.webSite;
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


