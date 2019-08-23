package com.wootecobook.turkey.friend.service.dto;

import com.wootecobook.turkey.friend.domain.FriendAsk;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendAskCreate {

    private Long receiverId;

    @Builder
    public FriendAskCreate(Long receiverId) {
        this.receiverId = receiverId;
    }

    public FriendAsk toEntity(Long sendUserId) {
        return FriendAsk.builder()
                .senderId(sendUserId)
                .receiverId(receiverId)
                .build();
    }
}
