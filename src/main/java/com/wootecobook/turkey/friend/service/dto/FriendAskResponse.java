package com.wootecobook.turkey.friend.service.dto;

import com.wootecobook.turkey.friend.domain.FriendAsk;
import com.wootecobook.turkey.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendAskResponse {

    private Long friendAskId;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;

    @Builder
    private FriendAskResponse(Long friendAskId, Long senderId, String senderName,
                              Long receiverId, String receiverName) {
        this.friendAskId = friendAskId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
    }

    public static FriendAskResponse from(FriendAsk friendAsk, User sender, User receiver) {
        return FriendAskResponse.builder()
                .friendAskId(friendAsk.getId())
                .senderId(sender.getId())
                .senderName(sender.getName())
                .receiverId(receiver.getId())
                .receiverName(receiver.getName())
                .build();
    }
}
