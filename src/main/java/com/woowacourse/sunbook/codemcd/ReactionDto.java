package com.woowacourse.sunbook.codemcd;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReactionDto {

    private Long numberOfGood;

    public ReactionDto(Long numberOfGood) {
        this.numberOfGood = numberOfGood;
    }
}
