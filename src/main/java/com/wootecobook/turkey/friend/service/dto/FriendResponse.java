package com.wootecobook.turkey.friend.service.dto;

import com.wootecobook.turkey.friend.domain.Friend;
import com.wootecobook.turkey.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendResponse {

    private Long friendId;
    private Long relatedUserId;
    private String relatedUserName;

    @Builder
    private FriendResponse(Long friendId, Long relatedUserId, String relatedUserName) {
        this.friendId = friendId;
        this.relatedUserId = relatedUserId;
        this.relatedUserName = relatedUserName;
    }

    public static FriendResponse from(Friend friend, User relatedUser) {
        return FriendResponse.builder()
                .friendId(friend.getId())
                .relatedUserId(relatedUser.getId())
                .relatedUserName(relatedUser.getName())
                .build();
    }
}
