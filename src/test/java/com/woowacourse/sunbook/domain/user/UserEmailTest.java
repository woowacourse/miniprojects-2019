package com.woowacourse.sunbook.domain.user;

import com.woowacourse.sunbook.domain.validation.exception.InvalidValueException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserEmailTest {

    @Test
    void 생성() {
        UserEmail userEmail = new UserEmail("bmo@bmo.com");
        assertThat(userEmail).isEqualTo(new UserEmail("bmo@bmo.com"));
    }

    @Test
    void 이메일_유효성_실패() {
        assertThrows(InvalidValueException.class, () -> new UserEmail("bmo@@bmo.com"));
    }

    @Test
    void 이메일_유효성_성공() {
        assertDoesNotThrow(() -> new UserEmail("bmo@bmo.com"));
    }
}
