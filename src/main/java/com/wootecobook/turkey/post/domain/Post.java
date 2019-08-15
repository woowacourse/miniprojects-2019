package com.wootecobook.turkey.post.domain;

import com.wootecobook.turkey.commons.BaseEntity;
import com.wootecobook.turkey.post.domain.exception.InvalidPostException;
import com.wootecobook.turkey.post.domain.exception.PostUpdateFailException;
import com.wootecobook.turkey.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Post extends BaseEntity {

    @Embedded
    private Contents contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_post_to_user"))
    private User author;

    public Post(final Contents contents, final User author) {
        validate(contents, author);

        this.contents = contents;
        this.author = author;
    }

    private void validate(final Contents contents, final User author) {
        if (contents == null || author == null) {
            throw new InvalidPostException();
        }
    }

    public Post update(Post other) {
        if (other == null) {
            throw new PostUpdateFailException();
        }

        if (!this.author.equals(other.author)) {
            // TODO: Exception Custom 하기
            throw new IllegalArgumentException();
        }

        this.contents = other.contents;

        return this;
    }
  
}
