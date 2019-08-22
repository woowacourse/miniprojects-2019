package com.wootecobook.turkey.comment.domain;

import com.wootecobook.turkey.commons.Good;
import com.wootecobook.turkey.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue("2")
public class CommentGood extends Good {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID", foreignKey = @ForeignKey(name = "FK_COMMENT_TO_GOOD"))
    private Comment comment;

    public CommentGood(final User user, final Comment comment) {
        super(user);
        this.comment = comment;
    }
}
