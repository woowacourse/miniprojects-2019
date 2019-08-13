package com.wootecobook.turkey.post.domain;

import com.wootecobook.turkey.post.domain.exception.InvalidContentException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
@Getter
@EqualsAndHashCode
public class Contents {

    private static final String EMPTY_CONTENTS_ERROR_MESSAGE = "내용이 없습니다.";

    @Lob
    @Column(nullable = false)
    private String contents;

    public Contents(final String contents) {
        validate(contents);
        this.contents = contents;
    }

    private void validate(String contents) {
        if (contents == null || contents.trim().equals("")) {
            throw new InvalidContentException(EMPTY_CONTENTS_ERROR_MESSAGE);
        }
    }
}