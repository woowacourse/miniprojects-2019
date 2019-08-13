package com.woowacourse.sunbook.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Embeddable
public class ArticleFeature {

    private final static Pattern URL_PATTERN = Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");

    @Column(nullable = false)
    private final String contents;

    @Column(nullable = false)
    private final String imageUrl;

    @Column(nullable = false)
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

    private void validateUrl(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        if (!matcher.find()) {
            throw new IllegalArgumentException("유효하지 않은 url입니다.");
        }
    }
}
