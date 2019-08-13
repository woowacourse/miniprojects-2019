package com.woowacourse.sunbook.domain.user;

import com.woowacourse.sunbook.domain.validation.exception.InvalidValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNameTest {
    @Test
    void 생성() {
        UserName userName = new UserName("에이든");
        assertEquals(userName, new UserName("에이든"));
    }

    @Test
    void 이름_유효성_실패() {
        assertThrows(InvalidValueException.class, () -> new UserName("bmo@@bmo"));
    }

    @Test
    void 이름_유효성_성공() {
        assertDoesNotThrow(() -> new UserName("aiden"));
    }
}