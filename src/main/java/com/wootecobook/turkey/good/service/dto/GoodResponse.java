package com.wootecobook.turkey.good.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoodResponse {

    private int totalGood;
    private boolean gooded;

    private GoodResponse(final int totalGood, final boolean gooded) {
        this.totalGood = totalGood;
        this.gooded = gooded;
    }

    public static GoodResponse init() {
        return new GoodResponse();
    }

    public static GoodResponse of(final int totalGood, final boolean gooded) {
        return new GoodResponse(totalGood, gooded);
    }
}
