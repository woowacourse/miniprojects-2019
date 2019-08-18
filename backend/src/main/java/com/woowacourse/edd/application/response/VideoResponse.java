package com.woowacourse.edd.application.response;

import java.util.Objects;

public class VideoResponse {

    private final Long id;
    private final String youtubeId;
    private final String title;
    private final String contents;
    private final String createDate;

    public VideoResponse(Long id, String youtubeId, String title, String contents, String createDate) {
        this.id = id;
        this.youtubeId = youtubeId;
        this.title = title;
        this.contents = contents;
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

    public String getContents() {
        return contents;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoResponse that = (VideoResponse) o;
        return id.equals(that.id) &&
            Objects.equals(youtubeId, that.youtubeId) &&
            Objects.equals(title, that.title) &&
            Objects.equals(contents, that.contents) &&
            Objects.equals(createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, youtubeId, title, contents, createDate);
    }
}
