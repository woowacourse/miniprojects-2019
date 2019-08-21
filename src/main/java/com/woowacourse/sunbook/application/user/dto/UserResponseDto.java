package com.woowacourse.sunbook.application.user.dto;

import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private UserEmail userEmail;
    private UserName userName;

    public UserResponseDto(Long id) {
        this.id = id;
    }
}
