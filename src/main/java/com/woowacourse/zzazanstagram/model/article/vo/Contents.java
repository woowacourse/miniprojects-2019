package com.woowacourse.zzazanstagram.model.article.vo;

import javax.persistence.Column;
import javax.persistence.Lob;

public class Contents {

    @Lob
    @Column(name = "contents")
    private final String contents;

    public Contents(final String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}