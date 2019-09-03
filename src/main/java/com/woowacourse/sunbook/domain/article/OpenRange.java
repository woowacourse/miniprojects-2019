package com.woowacourse.sunbook.domain.article;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OpenRange {
    ALL(0),
    ONLY_FRIEND(1),
    NONE(2);

    private Integer openRange;

    OpenRange(Integer openRange) {
        this.openRange = openRange;
    }

    public static OpenRange of(Integer openRange) {
        return Arrays.stream(values())
                .filter(or -> openRange.equals(or.openRange))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                ;
    }
}
