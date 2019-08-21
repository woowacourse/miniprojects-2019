package com.woowacourse.dsgram.service.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUserRequest {
    private long id;
    public static final String SESSION_USER = "sessionUser";

    private String email;
    private String nickName;
    private String userName;

    @Builder
    public LoginUserRequest(long id, String email, String nickName, String userName) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.userName = userName;
    }
}
