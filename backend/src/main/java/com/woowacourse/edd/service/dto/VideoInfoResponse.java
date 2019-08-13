package com.woowacourse.edd.service.dto;


import com.woowacourse.edd.domain.Video;
import com.woowacourse.edd.domain.vo.Contents;
import com.woowacourse.edd.domain.vo.Title;
import com.woowacourse.edd.domain.vo.YoutubeId;

import java.time.LocalDateTime;

public class VideoInfoResponse {

    private YoutubeId youtubeId;
    private Title title;
    private Contents contents;
    private LocalDateTime createDate;

    private VideoInfoResponse(Video video) {
        this.youtubeId = video.getYoutubeId();
        this.title = video.getTitle();
        this.contents = video.getContents();
        this.createDate = video.getCreateDate();
    }

    public static VideoInfoResponse of(Video video) {
        return new VideoInfoResponse(video);
    }

    public YoutubeId getYoutubeId() {
        return youtubeId;
    }

    public Title getTitle() {
        return title;
    }

    public Contents getContents() {
        return contents;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
