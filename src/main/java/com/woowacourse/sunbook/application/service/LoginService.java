package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.exception.LoginException;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LoginService(final UserRepository userRepository, final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDto login(final UserRequestDto userRequestDto) {
        User user = userRepository.findByUserEmailAndUserPassword(
                userRequestDto.getUserEmail(),
                userRequestDto.getUserPassword()
        ).orElseThrow(LoginException::new);

        return modelMapper.map(user, UserResponseDto.class);
    }
}
