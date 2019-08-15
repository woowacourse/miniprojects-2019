package com.woowacourse.edd.domain.vo;

import com.woowacourse.edd.exceptions.InvalidTitleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TitleTest {
    @Test
    void check_empty_test() {
        assertThrows(InvalidTitleException.class, () -> new Title(""));
    }

    @Test
    void check_null_test() {
        assertThrows(InvalidTitleException.class, () -> new Title(null));
    }

    @Test
    void check_blank_test() {
        assertThrows(InvalidTitleException.class, () -> new Title(" "));
    }

    @Test
    void valid_title_test() {
        assertDoesNotThrow(() -> new Title("abcd"));
    }
}