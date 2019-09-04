package com.woowacourse.sunbook.domain.user;

import com.woowacourse.sunbook.domain.validation.Validator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"firstName", "lastName"})
@Embeddable
public class UserName {
    public static final String FIRST_NAME_PATTERN = "^(?!.*[~`!@#$%\\^&*()-])(?!.*\\d).{2,25}$";
    public static final String LAST_NAME_PATTERN = "^(?!.*[~`!@#$%\\^&*()-])(?!.*\\d).{1,25}$";
    public static final String FIRST_NAME_EXCEPTION_MESSAGE = "올바르지 않은 이름입니다.";
    public static final String LAST_NAME_EXCEPTION_MESSAGE = "올바르지 않은 성입니다.";

    @Column(nullable = false, length = 25)
    private String firstName;

    @Column(nullable = false, length = 25)
    private String lastName;

    public UserName(String firstName, String lastName) {
        Validator.checkValid(firstName, FIRST_NAME_PATTERN, FIRST_NAME_EXCEPTION_MESSAGE);
        Validator.checkValid(lastName, LAST_NAME_PATTERN, LAST_NAME_EXCEPTION_MESSAGE);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }
}