package com.woowacourse.sunbook.presentation.support;

import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import lombok.Getter;

@Getter
public class LoginUser {
    private Long id;
    private UserEmail userEmail;
    private UserName userName;

    public LoginUser(UserResponseDto userResponseDto) {
        this.id = userResponseDto.getId();
        this.userEmail = userResponseDto.getUserEmail();
        this.userName = userResponseDto.getUserName();
    }
}
