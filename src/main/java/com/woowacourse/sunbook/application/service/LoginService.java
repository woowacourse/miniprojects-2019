package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.exception.DuplicateEmailException;
import com.woowacourse.sunbook.application.exception.LoginException;
import com.woowacourse.sunbook.application.exception.NotFoundUserException;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LoginService(final UserRepository userRepository, final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserResponseDto save(final UserRequestDto userRequestDto) {
        checkDuplicateEmail(userRequestDto);
        User user = userRepository.save(modelMapper.map(userRequestDto, User.class));

        return modelMapper.map(user, UserResponseDto.class);
    }

    private void checkDuplicateEmail(final UserRequestDto userRequestDto) {
        if (userRepository.existsByUserEmail(userRequestDto.getUserEmail())) {
            throw new DuplicateEmailException();
        }
    }

    public UserResponseDto login(final UserRequestDto userRequestDto) {
        User user = userRepository.findByUserEmailAndUserPassword(
                userRequestDto.getUserEmail(),
                userRequestDto.getUserPassword()
        ).orElseThrow(LoginException::new);

        return modelMapper.map(user, UserResponseDto.class);
    }

    protected User findById(final Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundUserException::new);
    }
}
