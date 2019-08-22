package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.UserConverter;
import com.woowacourse.edd.application.dto.UserRequestDto;
import com.woowacourse.edd.application.response.UserResponse;
import com.woowacourse.edd.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserInternalService userInternalService;
    private final UserConverter userConverter = new UserConverter();

    public UserService(UserInternalService userInternalService) {
        this.userInternalService = userInternalService;
    }

    public Long save(UserRequestDto userSaveRequestDto) {
        User user = userInternalService.save(userConverter.toSaveEntity(userSaveRequestDto));
        return user.getId();
    }

    public UserResponse update(Long id, Long loggedInId, UserRequestDto userRequestDto) {
        User user = userInternalService.update(id, loggedInId, userRequestDto);
        return userConverter.toResponse(user);
    }

    public void delete(Long id, Long loggedInId) {
        userInternalService.delete(id, loggedInId);
    }

    public UserResponse findbyId(Long id) {
        User user = userInternalService.findById(id);
        return userConverter.toResponse(user);
    }
}

