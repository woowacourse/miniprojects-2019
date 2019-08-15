package com.woowacourse.sunbook.domain.user;

import com.woowacourse.sunbook.domain.validation.Validator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode(of = "email")
@Embeddable
@NoArgsConstructor
public class UserEmail {
    private static final String EMAIL_PATTERN = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final String EMAIL_EXCEPTION_MESSAGE = "올바르지 않은 이메일입니다.";

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    public UserEmail(String email) {
        Validator.checkValid(email, EMAIL_PATTERN, EMAIL_EXCEPTION_MESSAGE);
        this.email = email;
    }
}
