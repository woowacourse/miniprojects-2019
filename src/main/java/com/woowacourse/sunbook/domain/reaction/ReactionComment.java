package com.woowacourse.sunbook.domain.reaction;

import com.woowacourse.sunbook.domain.BaseEntity;
import com.woowacourse.sunbook.domain.comment.Comment;
import com.woowacourse.sunbook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class ReactionComment extends BaseEntity {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment comment;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    private Boolean hasGood;

    public ReactionComment(final User author, final Comment comment) {
        this.comment = comment;
        this.author = author;
        this.hasGood = false;
    }

    public void addGood() {
        if (this.hasGood) {
            throw new IllegalReactionException();
        }

        this.hasGood = true;
    }

    public void removeGood() {
        if (this.hasGood) {
            this.hasGood = false;

            return;
        }

        throw new IllegalReactionException();
    }
}
