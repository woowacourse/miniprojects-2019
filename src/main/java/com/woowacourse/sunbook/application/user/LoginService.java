package com.woowacourse.sunbook.application.user;

import com.woowacourse.sunbook.application.exception.DuplicateEmailException;
import com.woowacourse.sunbook.application.exception.LoginException;
import com.woowacourse.sunbook.application.user.dto.UserRequestDto;
import com.woowacourse.sunbook.application.user.dto.UserResponseDto;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LoginService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto) {
        checkDuplicateEmail(userRequestDto);
        User user = userRepository.save(modelMapper.map(userRequestDto, User.class));
        return modelMapper.map(user, UserResponseDto.class);
    }

    private void checkDuplicateEmail(UserRequestDto userRequestDto) {
        if (userRepository.existsByUserEmail(userRequestDto.getUserEmail())) {
            throw new DuplicateEmailException();
        }
    }

    public UserResponseDto login(UserRequestDto userRequestDto) {
        User user = userRepository.findByUserEmailAndUserPassword(
                userRequestDto.getUserEmail(),
                userRequestDto.getUserPassword()
        ).orElseThrow(LoginException::new);

        return modelMapper.map(user, UserResponseDto.class);
    }
}
