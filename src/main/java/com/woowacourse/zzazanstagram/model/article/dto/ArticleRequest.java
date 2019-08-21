package com.woowacourse.zzazanstagram.model.article.dto;

public class ArticleRequest {
    private String contents;
    private String hashTag;

    public ArticleRequest() {
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
