package com.woowacourse.edd.domain.vo;

import com.woowacourse.edd.exceptions.InvalidTitleException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

@Embeddable
public class Contents {

    @Column
    @Lob
    private String contents;

    private Contents() {
    }

    public Contents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}
