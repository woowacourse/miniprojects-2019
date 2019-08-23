package com.woowacourse.sunbook.application.dto.user;

import com.woowacourse.sunbook.domain.user.UserChangePassword;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserUpdateRequestDto {
    private UserEmail userEmail;
    private UserName userName;
    private UserPassword userPassword;
    private UserChangePassword changePassword;

    public UserUpdateRequestDto(UserEmail userEmail, UserName userName, UserPassword userPassword, UserChangePassword changePassword) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.changePassword = changePassword;
    }
}
