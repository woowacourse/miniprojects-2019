package com.woowacourse.zzazanstagram.model.article.dto;

import java.time.LocalDateTime;

public class ArticleResponse {
    private Long id;
    private String image;
    private String contents;
    //    private String hashTag;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    //Todo author, 댓글, 좋아요 추가하자

    public ArticleResponse() {
    }

    public ArticleResponse(Long id, String image, String contents, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.image = image;
        this.contents = contents;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
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
