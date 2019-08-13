package com.woowacourse.sunbook.domain.user;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserEmail userEmail;

    @Embedded
    private UserPassword password;

    @Embedded
    private UserName name;

    public User(UserEmail email, UserPassword password, UserName name) {
        this.userEmail = email;
        this.password = password;
        this.name = name;
    }

    public void updateEmail(User user, UserEmail email) {
        checkValid(user);
        this.userEmail = email;
    }

    public void updatePassword(User user, UserPassword password) {
        checkValid(user);
        this.password = password;
    }

    public void updateName(User user, UserName name) {
        checkValid(user);
        this.name = name;
    }

    private void checkValid(User user) {
        if (this.equals(user)) {
            return;
        }
        throw new IllegalArgumentException();
    }
}
