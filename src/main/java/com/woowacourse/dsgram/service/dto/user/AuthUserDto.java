package com.woowacourse.dsgram.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserDto {
    private String email;
    private String password;

    public AuthUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
