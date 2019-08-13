package com.woowacourse.zzazanstagram.model.article.vo;

import javax.persistence.Column;

public class ImageUrl {

    @Column(name = "imageUrl", nullable = false)
    private final String imageUrl;

    public ImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}