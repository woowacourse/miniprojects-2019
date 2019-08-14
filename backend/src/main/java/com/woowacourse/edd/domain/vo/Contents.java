package com.woowacourse.edd.domain.vo;

import com.woowacourse.edd.exceptions.InvalidContentsException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

@Embeddable
public class Contents {

    @Column(nullable = false)
    @Lob
    private String contents;

    private Contents() {
    }

    public Contents(String contents) {
        checkContents(contents);
        this.contents = contents;
    }

    private void checkContents(String contents) {
        if (Objects.isNull(contents) || contents.trim().isEmpty()) {
            throw new InvalidContentsException();
        }
    }

    public String getContents() {
        return contents;
    }
}
