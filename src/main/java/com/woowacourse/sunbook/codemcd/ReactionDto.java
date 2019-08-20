package com.woowacourse.sunbook.codemcd;

import lombok.Getter;

@Getter
public class ReactionDto {

    private Boolean hasGood;

    public ReactionDto(Boolean hasGood) {
        this.hasGood = hasGood;
    }
}
