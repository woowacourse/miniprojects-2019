package com.woowacourse.sunbook.domain.fileurl;

import com.woowacourse.sunbook.domain.fileurl.exception.InvalidUrlException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileUrlTest {
    private static final String VALID_URL = "http://www.valid.com";
    private static final String INVALID_URL = "ABCDEF";

    @Test
    void 유효한_URL() {
        assertDoesNotThrow(() ->new FileUrl(VALID_URL));
    }

    @Test
    void 유효하지_않은_URL() {
        assertThrows(InvalidUrlException.class, () -> new FileUrl(INVALID_URL));
    }
}