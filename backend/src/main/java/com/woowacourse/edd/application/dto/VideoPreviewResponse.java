package com.woowacourse.edd.application.dto;


public class VideoPreviewResponse {

    private Long id;
    private String youtubeId;
    private String title;
    private String createDate;

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
