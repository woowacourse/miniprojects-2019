package com.woowacourse.edd.domain.vo;

import javax.persistence.Embeddable;

@Embeddable
public class Contents {

    private String contents;

    public Contents(){
    }

    private Contents(final String contents) {
        this.contents = contents;
    }

    public static Contents of(final String contents) {
        return new Contents(contents);
    }

    public String getContents() {
        return contents;
    }
}
