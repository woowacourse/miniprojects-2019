package com.wootecobook.turkey.friend.domain;

import com.wootecobook.turkey.commons.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class FriendAsk extends BaseEntity {

    @Column(nullable = false, updatable = false)
    private Long senderId;

    @Column(nullable = false, updatable = false)
    private Long receiverId;

    @Builder
    public FriendAsk(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }


    public List<Friend> createBidirectionalFriends() {
        return Arrays.asList(
                Friend.builder()
                        .relatingUserId(senderId)
                        .relatedUserId(receiverId)
                        .build(),
                Friend.builder()
                        .relatingUserId(receiverId)
                        .relatedUserId(senderId)
                        .build()
        );
    }
}
