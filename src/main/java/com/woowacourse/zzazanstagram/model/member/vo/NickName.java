package com.woowacourse.zzazanstagram.model.member.vo;

public class NickName {
    private static final String NAME_REGEX = "[A-Za-zㄱ-ㅎㅏ-ㅣ가-힣]{2,10}";

    private final String nickName;

    private NickName(final String name) {
        this.nickName = validateName(name);
    }

    public static NickName of(final String name) {
        return new NickName(name);
    }

    private String validateName(final String name) {
        if (!name.matches(NAME_REGEX)) {
            throw new IllegalArgumentException("이름은 2자 이상 10자 이하입니다.");
        }
        return name;
    }

    public NickName updateName(String name) {
        return new NickName(name);
    }

    public String getNickName() {
        return nickName;
    }
}
