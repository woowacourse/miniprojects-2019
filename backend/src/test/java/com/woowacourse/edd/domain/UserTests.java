package com.woowacourse.edd.domain;

import com.woowacourse.edd.exceptions.InvalidUserEmailException;
import com.woowacourse.edd.exceptions.InvalidUserNameException;
import com.woowacourse.edd.exceptions.InvalidUserPasswordException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTests extends BasicDomainTests {

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            new User("robby", "kangmin789@naver.com", "p@ssW0rd");
        });
    }

    @ParameterizedTest
    @MethodSource("invalidStrings")
    void create_invalid_name(final String invalidString) {
        assertThrows(InvalidUserNameException.class, () -> new User(invalidString, "kangmin789@naver.com", "p@ssWord"));
    }

    @ParameterizedTest
    @MethodSource("invalidStrings")
    void create_invalid_email(final String invalidString) {
        assertThrows(InvalidUserEmailException.class, () -> new User("robby", invalidString, "p@ssWord"));
    }

    @ParameterizedTest
    @MethodSource("invalidStrings")
    void create_invalid_password(final String invalidString) {
        assertThrows(InvalidUserPasswordException.class, () -> new User("robby", "kangmin789@naver.com", invalidString));
    }
}
