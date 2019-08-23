package com.wootecobook.turkey.friend.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendCreate {

    private Long friendAskId;

    @Builder
    public FriendCreate(Long friendAskId) {
        this.friendAskId = friendAskId;
    }
}
