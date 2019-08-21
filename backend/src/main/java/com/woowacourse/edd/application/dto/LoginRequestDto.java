package com.woowacourse.edd.application.dto;

public class LoginRequestDto {

    private String email;
    private String password;

    private LoginRequestDto() {
    }

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
