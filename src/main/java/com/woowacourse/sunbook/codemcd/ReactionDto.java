package com.woowacourse.sunbook.codemcd;

import lombok.Getter;

@Getter
public class ReactionDto {

    private Boolean hasGood;
    private Long numberOfGood;

    public ReactionDto(Boolean hasGood, Long numberOfGood) {
        this.hasGood = hasGood;
        this.numberOfGood = numberOfGood;
    }
}
