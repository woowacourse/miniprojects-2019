package com.woowacourse.zzazanstagram.model.like.service;

import com.woowacourse.zzazanstagram.model.like.dto.DdabongResponse;

public class DdabongAssembler {
    public static DdabongResponse toDto(int count, boolean isClicked){
        return new DdabongResponse(count, isClicked);
    }
}
