package com.wootecobook.turkey.friend.domain;

import com.wootecobook.turkey.commons.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Friend extends BaseEntity {

    @Column(nullable = false, updatable = false)
    private Long relatingUserId;

    @Column(nullable = false, updatable = false)
    private Long relatedUserId;

    @Builder
    private Friend(Long relatingUserId, Long relatedUserId) {
        this.relatingUserId = relatingUserId;
        this.relatedUserId = relatedUserId;
    }
}
