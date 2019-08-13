package com.woowacourse.sunbook.domain.user;

import com.woowacourse.sunbook.domain.validation.exception.InvalidValueException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserPasswordTest {

    @Test
    void 생성() {
        UserPassword userPassword = new UserPassword("12qw!@QW1");
        assertThat(userPassword).isEqualTo(new UserPassword("12qw!@QW1"));
    }

    @Test
    void 이메일_유효성_실패() {
        assertThrows(InvalidValueException.class, () -> new UserPassword("12qwasQW"));
    }

    @Test
    void 이메일_유효성_성공() {
        assertDoesNotThrow(() -> new UserPassword("12qw!@QW"));
    }
}