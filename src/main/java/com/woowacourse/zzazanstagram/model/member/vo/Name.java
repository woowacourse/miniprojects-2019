package com.woowacourse.zzazanstagram.model.member.vo;

import com.woowacourse.zzazanstagram.model.member.exception.MemberException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Name {
    private static final String NAME_REGEX = "[A-Za-zㄱ-ㅎㅏ-ㅣ가-힣]{2,10}";

    @Column(name = "name")
    private String name;

    private Name(final String name) {
        this.name = validateName(name);
    }

    private Name() {
    }

    public static Name of(final String name) {
        return new Name(name);
    }

    private String validateName(final String name) {
        if (!name.matches(NAME_REGEX)) {
            throw new MemberException("이름은 2자 이상 10자 이하입니다.");
        }
        return name;
    }

    public Name updateName(String name) {
        return new Name(name);
    }

    public String getName() {
        return name;
    }
}
