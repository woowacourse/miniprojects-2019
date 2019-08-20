package com.woowacourse.sunbook.seongmo;

import com.woowacourse.sunbook.application.user.dto.UserResponseDto;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import lombok.Getter;

@Getter
public class UserSession {
    private Long id;
    private UserEmail userEmail;
    private UserName userName;

    public UserSession(UserResponseDto userResponseDto) {
        this.id = userResponseDto.getId();
        this.userEmail = userResponseDto.getUserEmail();
        this.userName = userResponseDto.getUserName();
    }
}
