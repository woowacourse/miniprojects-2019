package com.woowacourse.edd.application.converter;

import com.woowacourse.edd.application.dto.UserRequestDto;
import com.woowacourse.edd.application.response.UserResponse;
import com.woowacourse.edd.domain.User;

public class UserConverter {

    private static final Boolean IS_DELETED = false;

    public User toSaveEntity(UserRequestDto userSaveRequestDto) {
        return new User(userSaveRequestDto.getName(), userSaveRequestDto.getEmail(), userSaveRequestDto.getPassword(), IS_DELETED);
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

}
