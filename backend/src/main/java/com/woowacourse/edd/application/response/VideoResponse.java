package com.woowacourse.edd.application.response;

public class VideoResponse {

    private final long id;
    private final String youtubeId;
    private final String title;
    private final String contents;
    private final String createDate;

    public VideoResponse(long id, String youtubeId, String title, String contents, String createDate) {
        this.id = id;
        this.youtubeId = youtubeId;
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
    }

    public long getId() {
        return id;
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

    public String getCreateDate() {
        return createDate;
    }
}
