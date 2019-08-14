package com.wootecobook.turkey.user.domain;

import com.wootecobook.turkey.commons.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Email
    @Column(unique = true)
    private String email;

    @Column
    private String name;

    @Column
    private String password;

    @Builder
    public User(String email, String name, String password) {
        UserValidator.validateEmail(email);
        UserValidator.validateName(name);
        UserValidator.validatePassword(password);

        this.email = email;
        this.name = name;
        this.password = password;
    }

}
