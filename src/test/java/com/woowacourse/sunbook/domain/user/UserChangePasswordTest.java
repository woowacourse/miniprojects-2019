package com.woowacourse.sunbook.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserChangePasswordTest {

    @Test
    void 생성() {
        UserChangePassword userChangePassword = new UserChangePassword("12qw!@QW1");
        assertThat(userChangePassword).isEqualTo(new UserChangePassword("12qw!@QW1"));
    }

    @Test
    void 변경암호_Empty일경우() {
        UserPassword userPassword = new UserPassword("12qw!@QW1");
        UserChangePassword userChangePassword = new UserChangePassword("");
        assertThat(userChangePassword.updatedPassword(userPassword)).isEqualTo(userPassword);
    }

    @Test
    void 변경암호_Not_Empty_일경우() {
        UserPassword userPassword = new UserPassword("12qw!@QW1");
        UserChangePassword userChangePassword = new UserChangePassword("12qw!Change");
        assertThat(userChangePassword.updatedPassword(userPassword)).isEqualTo(new UserPassword("12qw!Change"));
    }
}