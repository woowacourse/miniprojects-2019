package com.woowacourse.edd.application.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_EMAIL_FORM_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_EMAIL_SIZE_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.INVALID_NAME_MESSAGE;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.USER_EMAIL_PATTERN;
import static com.woowacourse.edd.application.dto.UserSaveRequestDto.USER_NAME_PATTERN;
import static com.woowacourse.edd.domain.User.EMAIL_LENGTH_MAX;
import static com.woowacourse.edd.domain.User.EMAIL_LENGTH_MIN;

public class UserUpdateRequestDto {

    @Pattern(regexp = USER_NAME_PATTERN, message = INVALID_NAME_MESSAGE)
    private String name;

    @Pattern(regexp = USER_EMAIL_PATTERN, message = INVALID_EMAIL_FORM_MESSAGE)
    @Size(min = EMAIL_LENGTH_MIN, max = EMAIL_LENGTH_MAX, message = INVALID_EMAIL_SIZE_MESSAGE)
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
