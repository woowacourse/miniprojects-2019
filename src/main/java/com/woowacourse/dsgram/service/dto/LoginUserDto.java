package com.woowacourse.dsgram.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUserDto {
    private String email;
    private String nickName;
    private String userName;

    @Builder
    public LoginUserDto(String email, String nickName, String userName) {
        this.email = email;
        this.nickName = nickName;
        this.userName = userName;
    }
}
