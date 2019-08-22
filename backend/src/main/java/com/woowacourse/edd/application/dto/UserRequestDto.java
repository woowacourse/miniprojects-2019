package com.woowacourse.edd.application.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class UserRequestDto {

    public static final String INVALID_NAME_MESSAGE = "이름은 2자이상 16자이하의 영문이나 숫자여야 합니다.";
    public static final String INVALID_EMAIL_MESSAGE = "올바르지 않은 이메일형식 입니다.";
    public static final String INVALID_PASSWORD_MESSAGE = "비밀번호는 8자이상의 영문 대,소문자, 특수문자의 조합이여야 합니다.";

    @Pattern(regexp = "^([A-Za-z가-힣]{2,16})$", message = INVALID_NAME_MESSAGE)
    private String name;

    @Email(message = INVALID_EMAIL_MESSAGE)
    private String email;

    @Pattern(regexp = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = INVALID_PASSWORD_MESSAGE)
    private String password;

    private UserRequestDto() {
    }

    public UserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
