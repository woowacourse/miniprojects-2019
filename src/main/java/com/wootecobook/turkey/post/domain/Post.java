package com.wootecobook.turkey.post.domain;

import com.wootecobook.turkey.commons.BaseEntity;
import com.wootecobook.turkey.post.domain.exception.InvalidPostException;
import com.wootecobook.turkey.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    private static final String NOT_POSTING_ERROR_MESSAGE = "포스팅 할 수 없습니다.";

    @Embedded
    private Contents contents;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_post_to_user"))
    private User author;

    public Post(final Contents contents, final User author) {
        validate(contents, author);

        this.contents = contents;
        this.author = author;
    }

    private void validate(final Contents contents, final User author) {
        if (contents == null || author == null) {
            throw new InvalidPostException(NOT_POSTING_ERROR_MESSAGE);
        }
    }

}
