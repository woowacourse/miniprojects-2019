package com.wootecobook.turkey.friend.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FriendAskTest {

    private static final Long RECEIVER_ID = 1L;
    private static final Long SENDER_ID = 2L;

    @Test
    void Friend_생성_테스트() {
        FriendAsk friendAsk = FriendAsk.builder()
                .receiverId(RECEIVER_ID)
                .senderId(SENDER_ID)
                .build();

        List<Friend> friends = friendAsk.createBidirectionalFriends();

        assertThat(friends.size()).isEqualTo(2);

        Friend firstFriend = friends.get(0);
        Friend secondFriend = friends.get(1);
        assertThat(firstFriend.getRelatedUserId()).isEqualTo(RECEIVER_ID);
        assertThat(firstFriend.getRelatingUserId()).isEqualTo(SENDER_ID);
        assertThat(secondFriend.getRelatedUserId()).isEqualTo(SENDER_ID);
        assertThat(secondFriend.getRelatingUserId()).isEqualTo(RECEIVER_ID);
    }
}
