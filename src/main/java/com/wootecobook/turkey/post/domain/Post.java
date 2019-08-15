package com.wootecobook.turkey.post.domain;

import com.wootecobook.turkey.commons.BaseEntity;
import com.wootecobook.turkey.post.domain.exception.InvalidPostException;
import com.wootecobook.turkey.post.domain.exception.PostUpdateFailException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Embedded
    private Contents contents;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_post_to_user"))
//    private User author;

    public Post(final Contents contents) {
        validate(contents);

        this.contents = contents;
    }

    private void validate(final Contents contents) {
        if (contents == null) {
            throw new InvalidPostException();
        }
    }

    public Post update(Post other) {
        if (other == null) {
            throw new PostUpdateFailException();
        }
        this.contents = other.contents;

        return this;
    }
}
