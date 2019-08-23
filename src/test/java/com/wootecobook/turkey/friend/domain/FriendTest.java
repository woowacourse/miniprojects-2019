package com.wootecobook.turkey.friend.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FriendTest {

    private static final Long RELATING_USER_ID = 1L;
    private static final Long RELATED_USER_ID = 2L;

    @Test
    void RelatingUserId_확인() {
        Friend friend = Friend.builder()
                .relatedUserId(RELATED_USER_ID)
                .relatingUserId(RELATING_USER_ID)
                .build();

        assertThat(friend.matchRelatingUserId(RELATING_USER_ID)).isTrue();
    }

    @Test
    void 잘못된_RelatingUserId_확인() {
        Friend friend = Friend.builder()
                .relatedUserId(RELATED_USER_ID)
                .relatingUserId(RELATING_USER_ID)
                .build();

        assertThat(friend.matchRelatingUserId(RELATED_USER_ID)).isFalse();
    }

    @Test
    void RelatedUserId_확인() {
        Friend friend = Friend.builder()
                .relatedUserId(RELATED_USER_ID)
                .relatingUserId(RELATING_USER_ID)
                .build();

        assertThat(friend.matchRelatedUserId(RELATED_USER_ID)).isTrue();
    }

    @Test
    void 잘못된_RelatedUserId_확인() {
        Friend friend = Friend.builder()
                .relatedUserId(RELATED_USER_ID)
                .relatingUserId(RELATING_USER_ID)
                .build();

        assertThat(friend.matchRelatedUserId(RELATING_USER_ID)).isFalse();
    }

    @Test
    void Null_입력() {
        assertThrows(IllegalArgumentException.class, () -> Friend.builder().build());
    }
}
