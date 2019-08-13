package com.woowacourse.sunbook.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class ArticleFeature {

    private final String contents;
    private final String imageUrl;
    private final String videoUrl;

    private ArticleFeature() {
        this.contents = "";
        this.imageUrl = "";
        this.videoUrl = "";
    }

    public ArticleFeature(String contents, String imageUrl, String videoUrl) {
        empty(contents, imageUrl, videoUrl);
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }

    private void empty(String contents, String imageUrl, String videoUrl) {
        if ("".equals(contents) && "".equals(imageUrl) && "".equals(videoUrl)) {
            throw new IllegalArgumentException("내용이 없습니다.");
        }
    }
}
