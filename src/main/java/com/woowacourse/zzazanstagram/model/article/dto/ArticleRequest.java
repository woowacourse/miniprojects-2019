package com.woowacourse.zzazanstagram.model.article.dto;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

public class ArticleRequest {

    @NotBlank
    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @Lob
    @Column(name = "contents")
    private String contents;

    @Column(name = "hashTag")
    private String hashTag;

    public ArticleRequest() {
    }

    public ArticleRequest(String imageUrl, String contents, String hashTag) {
        this.imageUrl = imageUrl;
        this.contents = contents;
        this.hashTag = hashTag;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }
}
