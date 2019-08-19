package com.woowacourse.edd.application.response;


public class VideoPreviewResponse {

    private final Long id;
    private final String youtubeId;
    private final String title;
    private final String createDate;

    public VideoPreviewResponse(Long id, String youtubeId, String title, String createDate) {
        this.id = id;
        this.youtubeId = youtubeId;
        this.title = title;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public String getTitle() {
        return title;
    }

    public String getCreateDate() {
        return createDate;
    }
}
