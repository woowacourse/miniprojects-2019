package com.woowacourse.sunbook.application.user.dto;

import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUpdateRequestDto {
    @Getter private UserEmail userEmail;
    @Getter private UserName userName;
    @Getter private UserPassword userPassword;
    private String changePassword;

    public UserPassword getChangePassword() {
        if (changePassword.isEmpty()) {
            return userPassword;
        }
        return new UserPassword(changePassword);
    }
}
