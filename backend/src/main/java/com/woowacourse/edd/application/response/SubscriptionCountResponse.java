package com.woowacourse.edd.application.response;

public class SubscriptionCountResponse {

    private final int count;

    public SubscriptionCountResponse(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
