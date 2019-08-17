package com.woowacourse.sunbook.application.dto.user;

import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private Long id;
    private UserEmail userEmail;
    private UserName userName;
}
