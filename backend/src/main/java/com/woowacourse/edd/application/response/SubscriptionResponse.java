package com.woowacourse.edd.application.response;

public class SubscriptionResponse {

    private final Long id;
    private final String name;

    public SubscriptionResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
