package com.woowacourse.zzazanstagram.model.like.dto;

public class DdabongResponse {
    private int count;
    private boolean isClicked;

    public DdabongResponse() {
    }

    public DdabongResponse(int count, boolean isClicked) {
        this.count = count;
        this.isClicked = isClicked;
    }

    public int getCount() {
        return count;
    }

    public boolean isClicked() {
        return isClicked;
    }
}
