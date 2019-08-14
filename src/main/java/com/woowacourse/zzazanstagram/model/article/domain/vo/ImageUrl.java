package com.woowacourse.zzazanstagram.model.article.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ImageUrl {

    @Column(name = "image_url", nullable = false)
    private String url;

    private ImageUrl() {
    }

    private ImageUrl(final String url) {
        this.url = url;
    }

    public static ImageUrl of(final String imageUrl) {
        return new ImageUrl(imageUrl);
    }

    public String getUrl() {
        return url;
    }
}