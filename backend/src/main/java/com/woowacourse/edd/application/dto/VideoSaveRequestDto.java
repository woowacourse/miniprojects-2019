package com.woowacourse.edd.application.dto;

public class VideoSaveRequestDto {

    private String youtubeId;
    private String title;
    private String contents;

    private VideoSaveRequestDto() {
    }

    public VideoSaveRequestDto(String youtubeId, String title, String contents) {
        this.youtubeId = youtubeId;
        this.title = title;
        this.contents = contents;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
