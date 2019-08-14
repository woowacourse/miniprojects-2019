package com.woowacourse.sunbook.application.dto;

import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private UserEmail userEmail;
    private UserName userName;
    private UserPassword userPassword;
}
