package com.woowacourse.edd.domain.vo;

import com.woowacourse.edd.exceptions.InvalidContentsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentsTest {
    @Test
    void check_empty_test() {
        assertThrows(InvalidContentsException.class, () -> new Contents(""));
    }

    @Test
    void check_null_test() {
        assertThrows(InvalidContentsException.class, () -> new Contents(null));
    }

    @Test
    void check_blank_test() {
        assertThrows(InvalidContentsException.class, () -> new Contents(" "));
    }

    @Test
    void valid_contents_test() {
        assertDoesNotThrow(() -> new Contents("This is contents!"));
    }
}
