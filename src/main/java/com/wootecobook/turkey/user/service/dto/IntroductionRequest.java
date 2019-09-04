package com.wootecobook.turkey.user.service.dto;

import com.wootecobook.turkey.user.domain.Introduction;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class IntroductionRequest {

    private Long id;
    private String currentCity;
    private String hometown;
    private String company;
    private String education;
    private Long userId;

    @Builder
    private IntroductionRequest(Long id, String currentCity, String hometown, String company, String education, Long userId) {
        this.id = id;
        this.currentCity = currentCity;
        this.hometown = hometown;
        this.company = company;
        this.education = education;
        this.userId = userId;
    }

    public Introduction toEntity() {
        return Introduction.builder()
                .company(company)
                .currentCity(currentCity)
                .education(education)
                .hometown(hometown)
                .userId(userId)
                .build();
    }
}
