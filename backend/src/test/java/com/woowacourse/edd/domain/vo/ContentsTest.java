package com.woowacourse.edd.domain.vo;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ContentsTest {

    @Test
    void 올바른_contents_test() {
        assertDoesNotThrow(() -> new Contents(""));
    }
}
