package com.woowacourse.sunbook.domain.user;

import com.woowacourse.sunbook.domain.validation.Validator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode(of = "password")
@Embeddable
@NoArgsConstructor
public class UserPassword {
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~`$@$!%?&])[A-Za-z\\d$@$!%?&]{8,20}$";
    public static final String PASSWORD_EXCEPTION_MESSAGE = "올바른 비밀번호가 아닙니다.";

    @Column(nullable = false, length = 20)
    private String password;

    public UserPassword(String password) {
        Validator.checkValid(password, PASSWORD_PATTERN, PASSWORD_EXCEPTION_MESSAGE);
        this.password = password;
    }
}
