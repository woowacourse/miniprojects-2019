package com.woowacourse.sunbook.domain.user;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "changePassword")
@Embeddable
public class UserChangePassword {
    private String changePassword;

    public UserChangePassword(String changePassword) {
        this.changePassword = changePassword;
    }

    public UserPassword updatedPassword(final UserPassword userPassword) {
        if (changePassword.isEmpty()) {
            return userPassword;
        }

        return new UserPassword(changePassword);
    }
}
