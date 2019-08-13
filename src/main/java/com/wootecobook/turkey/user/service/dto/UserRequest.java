package com.wootecobook.turkey.user.service.dto;

import com.wootecobook.turkey.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRequest {

    private String email;
    private String name;
    private String password;

    @Builder
    public UserRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
