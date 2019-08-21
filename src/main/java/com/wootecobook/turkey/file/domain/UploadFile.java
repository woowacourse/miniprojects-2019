package com.wootecobook.turkey.file.domain;

import com.wootecobook.turkey.commons.domain.UpdatableEntity;
import com.wootecobook.turkey.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class UploadFile extends UpdatableEntity {

    @Embedded
    private FileFeature fileFeature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_fileentity_to_user"))
    private User owner;

    public UploadFile(FileFeature fileFeature, User owner) {
        this.fileFeature = fileFeature;
        this.owner = owner;
    }

    public boolean isOwner(User user) {
        return owner.equals(user);
    }
}
