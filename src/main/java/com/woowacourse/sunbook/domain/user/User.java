package com.woowacourse.sunbook.domain.user;

import com.woowacourse.sunbook.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class User extends BaseEntity {

    @Embedded
    private UserEmail userEmail;

    @Embedded
    private UserPassword userPassword;

    @Embedded
    private UserName userName;

    public User(UserEmail email, UserPassword userPassword, UserName userName) {
        this.userEmail = email;
        this.userPassword = userPassword;
        this.userName = userName;
    }

    public void updateEmail(User user, UserEmail email) {
        checkValid(user);
        this.userEmail = email;
    }

    public void updatePassword(User user, UserPassword password) {
        checkValid(user);
        this.userPassword = password;
    }

    public void updateName(User user, UserName name) {
        checkValid(user);
        this.userName = name;
    }

    private void checkValid(User user) {
        if (this.equals(user)) {
            return;
        }

        throw new IllegalArgumentException();
    }
}
