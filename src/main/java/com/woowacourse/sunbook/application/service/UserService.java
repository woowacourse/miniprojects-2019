package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.dto.user.UserUpdateRequestDto;
import com.woowacourse.sunbook.application.exception.LoginException;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserChangePassword;
import com.woowacourse.sunbook.domain.user.UserPassword;
import com.woowacourse.sunbook.domain.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(final UserRepository userRepository, final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserResponseDto update(final UserResponseDto loginUser, final UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findByUserEmailAndUserPassword(
                loginUser.getUserEmail(),
                userUpdateRequestDto.getUserPassword()
        ).orElseThrow(LoginException::new);

        UserPassword loginUserPassword = userUpdateRequestDto.getUserPassword();
        UserChangePassword userChangePassword = userUpdateRequestDto.getChangePassword();

        user.updateEmail(user, userUpdateRequestDto.getUserEmail());
        user.updateName(user, userUpdateRequestDto.getUserName());
        user.updatePassword(user, userChangePassword.updatedPassword(loginUserPassword));

        return modelMapper.map(user, UserResponseDto.class);
    }
}
