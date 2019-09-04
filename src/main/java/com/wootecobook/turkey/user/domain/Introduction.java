package com.wootecobook.turkey.user.domain;

import com.wootecobook.turkey.commons.domain.UpdatableEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Introduction extends UpdatableEntity {

    @Column
    private String currentCity;

    @Column
    private String hometown;

    @Column
    private String company;

    @Column
    private String education;

    @Column(nullable = false, updatable = false, unique = true, name = "user_id")
    private Long userId;

    @Builder
    private Introduction(String currentCity, String hometown, String company, String education, Long userId) {
        this.currentCity = currentCity;
        this.hometown = hometown;
        this.company = company;
        this.education = education;
        this.userId = userId;
    }

    public void update(Introduction introduction) {
        this.currentCity = introduction.currentCity;
        this.hometown = introduction.hometown;
        this.company = introduction.company;
        this.education = introduction.education;
    }
}
