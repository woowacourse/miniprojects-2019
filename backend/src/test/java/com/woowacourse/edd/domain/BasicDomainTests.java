package com.woowacourse.edd.domain;

import java.util.stream.Stream;

public class BasicDomainTests {
    protected static final String EMPTY = "";
    protected static final String BLANK = " ";

    protected static Stream<String> invalidStrings() {
        return Stream.of(EMPTY, BLANK, null);
    }
}
