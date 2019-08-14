package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
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

    public void update(User updatedUser) {
        // TODO: 2019-08-14 Nick name 중복 확인
        this.intro = updatedUser.intro;
        this.userName = updatedUser.userName;
        this.nickName = updatedUser.nickName;
        this.password = updatedUser.password;
        this.webSite = updatedUser.webSite;
    }
}


