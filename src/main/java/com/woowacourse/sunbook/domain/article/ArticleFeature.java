package com.woowacourse.sunbook.domain.article;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@Embeddable
public class ArticleFeature {
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
    private static final String EMPTY = "";

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String videoUrl;

    public ArticleFeature(final String contents, final String imageUrl, final String videoUrl) {
        checkEmpty(contents, imageUrl, videoUrl);
        validateUrl(imageUrl);
        validateUrl(videoUrl);
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }

    private void checkEmpty(final String contents, final String imageUrl, final String videoUrl) {
        if (EMPTY.equals(contents) && EMPTY.equals(imageUrl) && EMPTY.equals(videoUrl)) {
            throw new IllegalArgumentException("내용이 없습니다.");
        }
    }

    private void validateUrl(final String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        if (matcher.find() || EMPTY.equals(url)) {
            return;
        }
        throw new IllegalArgumentException("유효하지 않은 url입니다.");
    }
}
