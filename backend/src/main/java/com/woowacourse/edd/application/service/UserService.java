package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.UserConverter;
import com.woowacourse.edd.application.dto.UserSaveRequestDto;
import com.woowacourse.edd.application.dto.UserUpdateRequestDto;
import com.woowacourse.edd.application.response.UserResponse;
import com.woowacourse.edd.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserInternalService userInternalService;

    public UserService(UserInternalService userInternalService) {
        this.userInternalService = userInternalService;
    }

    public Long save(UserSaveRequestDto userSaveRequestDto) {
        User user = userInternalService.save(UserConverter.toSaveEntity(userSaveRequestDto));
        return user.getId();
    }

    public UserResponse findById(Long id) {
        User user = userInternalService.findById(id);
        return UserConverter.toResponse(user);
    }

    public UserResponse update(Long id, Long loggedInId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userInternalService.update(id, loggedInId, UserConverter.escapeUpdateRequestDto(userUpdateRequestDto));
        return UserConverter.toResponse(user);
    }

    public void delete(Long id, Long loggedInId) {
        userInternalService.delete(id, loggedInId);
    }
}

