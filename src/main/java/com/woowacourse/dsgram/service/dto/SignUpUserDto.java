package com.woowacourse.dsgram.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpUserDto {
    @Size(min = 2, max = 10)
    private String nickName;

    @Size(min = 2, max = 10)
    private String userName;

    @Size(min = 4, max = 16)
    private String password;

    @Email
    private String email;

    @Builder
    public SignUpUserDto(@Size(min = 2, max = 10) String nickName, @Size(min = 2, max = 10) String userName, @Size(min = 4, max = 16) String password, @Email String email) {
        this.nickName = nickName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}
