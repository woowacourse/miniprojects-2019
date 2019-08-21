package com.woowacourse.sunbook.mir.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "contents")
@Embeddable
public class CommentFeature {

    @Lob
    @Column(nullable = false)
    private String contents;

   public CommentFeature(final String contents) {
       this.contents = contents;
   }
}