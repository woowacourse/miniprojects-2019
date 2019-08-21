package com.woowacourse.sunbook.application.dto.reaction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReactionDto {
    private Long numberOfGood;

    public ReactionDto(final Long numberOfGood) {
        this.numberOfGood = numberOfGood;
    }
}
