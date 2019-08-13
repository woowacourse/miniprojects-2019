package com.woowacourse.zzazanstagram.model.article.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ImageUrl {

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    public ImageUrl() {
    }

    public ImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}