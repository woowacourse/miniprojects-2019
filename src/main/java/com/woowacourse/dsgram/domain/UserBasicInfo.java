package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBasicInfo {
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

    public UserBasicInfo(String email, String nickName, String userName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.userName = userName;
        this.password = password;
    }
}
