package com.woowacourse.zzazanstagram.model.ddabong.service;

import com.woowacourse.zzazanstagram.model.ddabong.dto.DdabongResponse;

public class DdabongAssembler {
    public static DdabongResponse toDto(int count, boolean isClicked) {
        return new DdabongResponse(count, isClicked);
    }
}
