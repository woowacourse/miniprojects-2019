package com.woowacourse.zzazanstagram.model.article.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Contents {

    @Lob
    @Column(name = "contents")
    private String contents;

    public Contents() {
    }

    public Contents(final String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}