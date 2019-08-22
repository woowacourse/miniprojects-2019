package com.wootecobook.turkey.commons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoodResponse {

    private int totalGood;

    public GoodResponse(final int totalGood) {
        this.totalGood = totalGood;
    }
}
