package com.woowacourse.edd.domain;

import com.woowacourse.edd.exceptions.InvalidUserEmailException;
import com.woowacourse.edd.exceptions.InvalidUserNameException;
import com.woowacourse.edd.exceptions.InvalidUserPasswordException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Where(clause = "is_deleted = 'false'")
public class User {

    public static final int EMAIL_LENGTH_MIN = 1;
    public static final int EMAIL_LENGTH_MAX = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_name")
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateDate;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted;

    protected User() {
    }

    public User(String name, String email, String password) {
        checkName(name);
        checkEmail(email);
        checkPassword(password);
        this.name = name;
        this.email = email;
        this.password = password;
        this.isDeleted = false;
    }

    private void checkName(String name) {
        if (Objects.isNull(name) || name.trim().isEmpty()) {
            throw new InvalidUserNameException();
        }
    }

    private void checkEmail(String email) {
        if (Objects.isNull(email) || email.trim().isEmpty()) {
            throw new InvalidUserEmailException();
        }
    }

    private void checkPassword(String password) {
        if (Objects.isNull(password) || password.trim().isEmpty()) {
            throw new InvalidUserPasswordException();
        }
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isNotMatchPassword(String password) {
        return !this.password.equals(password);
    }

    public boolean isNotMatch(Long userId) {
        return id != userId;
    }
}
