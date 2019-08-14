package com.wootecobook.turkey.comment.domain;

import com.wootecobook.turkey.commons.BaseEntity;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_comment_to_user"), nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_post_to_article"), nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder
    public Comment(final String contents, final User user, final Post post, final Comment parent) {
        this.contents = contents;
        this.user = user;
        this.post = post;
        this.parent = parent;
    }

    public boolean isWrittenBy(Long userId) {
        if(this.user.getId().equals(userId)){
            return true;
        }
        throw new CommentAuthException();
    }
}
