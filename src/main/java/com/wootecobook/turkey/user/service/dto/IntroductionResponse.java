package com.wootecobook.turkey.user.service.dto;

import com.wootecobook.turkey.user.domain.Introduction;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class IntroductionResponse {

    private Long id;
    private String currentCity;
    private String hometown;
    private String company;
    private String education;
    private Long userId;

    @Builder
    private IntroductionResponse(Long id, String currentCity, String hometown, String company, String education, Long userId) {
        this.id = id;
        this.currentCity = currentCity;
        this.hometown = hometown;
        this.company = company;
        this.education = education;
        this.userId = userId;
    }

    public static IntroductionResponse from(Introduction introduction) {
        return IntroductionResponse.builder()
                .id(introduction.getId())
                .hometown(introduction.getHometown())
                .company(introduction.getCompany())
                .currentCity(introduction.getCurrentCity())
                .education(introduction.getEducation())
                .userId(introduction.getUserId())
                .build();
    }
}
