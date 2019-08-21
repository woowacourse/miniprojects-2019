package com.wootecobook.turkey.post.domain;

import com.wootecobook.turkey.commons.domain.UpdatableEntity;
import com.wootecobook.turkey.file.domain.UploadFile;
import com.wootecobook.turkey.post.domain.exception.InvalidPostException;
import com.wootecobook.turkey.post.domain.exception.PostUpdateFailException;
import com.wootecobook.turkey.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends UpdatableEntity {

    @Embedded
    private Contents contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_post_to_user"))
    private User author;

    @OneToMany
    @JoinTable(name = "post_file",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<UploadFile> fileEntities = new ArrayList<>();

    @Builder
    private Post(final Long id, final Contents contents, final User author, List<UploadFile> fileEntities) {
        if (id == null) {
            validateAuthor(author);
        }
        validateContents(contents);

        this.contents = contents;
        this.author = author;
        this.fileEntities = fileEntities;
    }

    private void validateContents(final Contents contents) {
        if (contents == null) {
            throw new InvalidPostException();
        }
    }

    private void validateAuthor(final User author) {
        if (author == null) {
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

    public boolean isWrittenBy(final Long userId) {
        return author.matchId(userId);
    }
}
