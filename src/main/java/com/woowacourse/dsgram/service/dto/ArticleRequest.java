package com.woowacourse.dsgram.service.dto;

import org.springframework.web.multipart.MultipartFile;

public class ArticleRequest {

    private String contents;
    private String hashtag;
    private MultipartFile file;

    public ArticleRequest() {
    }

    public ArticleRequest(String contents, String hashtag, MultipartFile file) {
        this.contents = contents;
        this.hashtag = hashtag;
        this.file = file;
    }


    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "ArticleRequest{" +
                "contents='" + contents + '\'' +
                ", hashtag='" + hashtag + '\'' +
                ", file=" + file +
                '}';
    }
}
