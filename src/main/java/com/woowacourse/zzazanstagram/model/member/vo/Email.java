package com.woowacourse.zzazanstagram.model.member.vo;

import javax.persistence.Embeddable;

@Embeddable
public class Email {
    private static final String EMAIL_REGEX = "^[_a-zA-Z0-9-.]+@[.a-zA-Z0-9-]+\\.[a-zA-Z]+$";

    private String email;

    private Email(final String email) {
        this.email = validateEmail(email);
    }

    private Email() {
    }

    public static Email of(final String email) {
        return new Email(email);
    }

    private String validateEmail(final String email) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("잘못된 형식의 이메일입니다.");
        }
        return email;
    }

    public String getEmail() {
        return email;
    }
}
