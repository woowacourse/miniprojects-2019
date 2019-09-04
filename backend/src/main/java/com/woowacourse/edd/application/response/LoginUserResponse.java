package com.woowacourse.edd.application.response;

public class LoginUserResponse {

    private final Long id;
    private final String name;

    public LoginUserResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
