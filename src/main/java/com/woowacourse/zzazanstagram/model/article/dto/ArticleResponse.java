package com.woowacourse.zzazanstagram.model.article.dto;

import java.time.LocalDateTime;

public class ArticleResponse {
    private Long id;
    private String imageUrl;
    private String contents;
//    private String hashTag;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    //Todo author, 댓글, 좋아요 추가하자

    public ArticleResponse() {
    }

    public ArticleResponse(Long id, String imageUrl, String contents, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.contents = contents;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }
}
