package com.woowacourse.edd.domain.vo;

import com.woowacourse.edd.exceptions.InvalidTitleException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TitleTest {

    @Test
    void 올바른_title_검사() {
        assertDoesNotThrow(() -> new Title("abcd"));
    }

    @Test
    void title이_null_검사() {
        assertThrows(InvalidTitleException.class, () -> new Title(null));
    }

    @Test
    void title이_공백_검사() {
        assertThrows(InvalidTitleException.class, () -> new Title(""));
    }
}
