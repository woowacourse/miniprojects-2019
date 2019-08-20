package com.woowacourse.edd.application.dto;

public class VideoUpdateRequestDto {
    private String youtubeId;
    private String title;
    private String contents;

    public VideoUpdateRequestDto() {
    }

    public VideoUpdateRequestDto(String youtubeId, String title, String contents) {
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
