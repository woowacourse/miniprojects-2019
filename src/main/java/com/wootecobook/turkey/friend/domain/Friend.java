package com.wootecobook.turkey.friend.domain;

import com.wootecobook.turkey.commons.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Entity
@NoArgsConstructor
public class Friend extends BaseEntity {

    private static final String NULL_INPUT_MESSAGE = "Null 값을 입력할 수 없습니다.";

    @Column(nullable = false, updatable = false)
    private Long relatingUserId;

    @Column(nullable = false, updatable = false)
    private Long relatedUserId;

    @Builder
    private Friend(Long relatingUserId, Long relatedUserId) {
        validateNotNull(relatingUserId);
        validateNotNull(relatedUserId);
        this.relatingUserId = relatingUserId;
        this.relatedUserId = relatedUserId;
    }

    private void validateNotNull(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException(NULL_INPUT_MESSAGE);
        }
    }

    public boolean matchRelatingUserId(Long userId) {
        return relatingUserId.equals(userId);
    }

    public boolean matchRelatedUserId(Long userId) {
        return relatedUserId.equals(userId);
    }
}
