package com.wootecobook.turkey.user.service.dto;

import com.wootecobook.turkey.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private String email;
    private String name;

    @Builder
    public UserResponse(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
