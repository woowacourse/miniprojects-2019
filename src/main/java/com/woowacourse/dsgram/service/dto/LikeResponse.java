package com.woowacourse.dsgram.service.dto;

public class LikeResponse {

    private long countOfLikes;
    private boolean likeState;

    public LikeResponse(long countOfLikes, boolean likeState) {
        this.countOfLikes = countOfLikes;
        this.likeState = likeState;
    }
}
