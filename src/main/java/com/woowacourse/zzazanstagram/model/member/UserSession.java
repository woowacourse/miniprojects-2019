package com.woowacourse.zzazanstagram.model.member;

public class UserSession {
    private String email;

    public UserSession(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
