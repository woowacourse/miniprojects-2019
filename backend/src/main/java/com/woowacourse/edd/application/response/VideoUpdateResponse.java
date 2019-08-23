package com.woowacourse.edd.application.response;

public class VideoUpdateResponse {
    private final Long id;

    public VideoUpdateResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
