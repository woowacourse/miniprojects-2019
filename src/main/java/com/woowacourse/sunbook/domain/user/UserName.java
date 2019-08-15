package com.woowacourse.sunbook.domain.user;

import com.woowacourse.sunbook.domain.validation.Validator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode(of = "name")
@Embeddable
@NoArgsConstructor
public class UserName {
    public static final String NAME_PATTERN = "^(?!.*[~`!@#$%\\^&*()-])(?!.*\\d).{2,25}$";
    public static final String NAME_EXCEPTION_MESSAGE = "올바르지 않은 이름입니다.";

    @Column(nullable = false, length = 25)
    private String name;

    public UserName(String name) {
        Validator.checkValid(name, NAME_PATTERN, NAME_EXCEPTION_MESSAGE);
        this.name = name;
    }
}