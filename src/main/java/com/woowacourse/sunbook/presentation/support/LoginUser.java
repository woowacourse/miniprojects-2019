package com.woowacourse.sunbook.presentation.support;

import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import lombok.Getter;

@Getter
public class LoginUser {
    private final UserResponseDto userResponseDto;

    public LoginUser(final UserResponseDto userResponseDto) {
        this.userResponseDto = userResponseDto;
    }

    public Long getId() {
        return userResponseDto.getId();
    }

    public UserEmail getEmail() {
        return userResponseDto.getUserEmail();
    }

    public UserName getName() {
        return userResponseDto.getUserName();
    }
}
