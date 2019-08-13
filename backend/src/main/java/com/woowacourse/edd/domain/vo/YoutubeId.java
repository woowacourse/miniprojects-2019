package com.woowacourse.edd.domain.vo;

import javax.persistence.Embeddable;

@Embeddable
public class YoutubeId {

    private String youtubeId;

    public YoutubeId(){
    }
    private YoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public static YoutubeId of(String youtubeId) {
        return new YoutubeId(youtubeId);
    }

    public String getYoutubeId() {
        return youtubeId;
    }
}
