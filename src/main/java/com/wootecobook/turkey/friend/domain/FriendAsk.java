package com.wootecobook.turkey.friend.domain;

import com.wootecobook.turkey.commons.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FriendAsk extends BaseEntity {

    private static final String NULL_INPUT_MESSAGE = "Null 값을 입력할 수 없습니다.";

    @Column(nullable = false, updatable = false)
    private Long senderId;

    @Column(nullable = false, updatable = false)
    private Long receiverId;

    @Builder
    public FriendAsk(Long senderId, Long receiverId) {
        validateNotNull(senderId);
        validateNotNull(receiverId);
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    private void validateNotNull(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException(NULL_INPUT_MESSAGE);
        }
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

    public boolean matchUserId(Long userId) {
        return receiverId.equals(userId) || senderId.equals(userId);
    }
}
