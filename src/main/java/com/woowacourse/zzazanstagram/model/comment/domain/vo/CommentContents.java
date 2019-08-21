package com.woowacourse.zzazanstagram.model.comment.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class CommentContents {

    @Size(max = 100, min = 1, message = "댓글의 내용은 1글자 이상 100글자 이하만 가능합니다.")
    @Column(name = "contents", length = 100)
    private String contents;

    private CommentContents() {
    }

    private CommentContents(final String contents) {
        this.contents = contents;
    }

    public static CommentContents of(String contents) {
        return new CommentContents(contents);
    }

    public String getContents() {
        return contents;
    }
}