package com.woowacourse.dsgram.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {
    private long id;
    private String userName;
    private String nickName;
    private String password;
    private String webSite;
    private String intro;

    public UserDto(long id, String userName, String nickName, String password, String webSite, String intro) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.webSite = webSite;
        this.intro = intro;
    }
}
