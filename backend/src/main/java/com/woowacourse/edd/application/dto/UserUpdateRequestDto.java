package com.woowacourse.edd.application.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_EMAIL_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_NAME_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.USER_NAME_PATTERN;

public class UserUpdateRequestDto {

    @Pattern(regexp = USER_NAME_PATTERN, message = INVALID_NAME_MESSAGE)
    private String name;

    @Email(message = INVALID_EMAIL_MESSAGE)
    private String email;

    private UserUpdateRequestDto() {
    }

    public UserUpdateRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
