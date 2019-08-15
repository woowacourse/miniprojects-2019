package com.woowacourse.zzazanstagram.model.member;

public class MemberSession {
    private String email;

    public MemberSession(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
