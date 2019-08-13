package com.woowacourse.edd.domain.vo;

import javax.persistence.Embeddable;

@Embeddable
public class Title {

    private String title;

    public Title(){
    }

    private Title(final String title) {
        this.title = title;
    }

    public static Title of(final String title) {
        return new Title(title);
    }

    public String getTitle() {
        return title;
    }
}
