package com.woowacourse.edd.application.dto;

public class CommentRequestDto {

    private String contents;

    private CommentRequestDto() {
    }

    public CommentRequestDto(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}
