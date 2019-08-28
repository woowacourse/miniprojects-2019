package com.woowacourse.sunbook.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "contents")
@Embeddable
public class Content {

    @Lob
    @Column(nullable = false)
    private String contents;

   public Content(final String contents) {
       this.contents = contents;
   }
}