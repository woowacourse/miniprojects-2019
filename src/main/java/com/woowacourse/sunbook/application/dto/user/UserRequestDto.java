package com.woowacourse.sunbook.application.dto.user;

import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRequestDto {
    private UserEmail userEmail;
    private UserName userName;
    private UserPassword userPassword;

    public UserRequestDto(final UserEmail userEmail, final UserName userName, final UserPassword userPassword) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }
}
