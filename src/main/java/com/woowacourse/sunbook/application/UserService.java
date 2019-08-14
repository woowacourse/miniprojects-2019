package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.application.dto.UserRequestDto;
import com.woowacourse.sunbook.application.dto.UserResponseDto;
import com.woowacourse.sunbook.application.exception.LoginException;
import com.woowacourse.sunbook.domain.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto save(UserRequestDto userRequestDto) {
        User user = userRepository.save(
                new User(
                        userRequestDto.getUserEmail(),
                        userRequestDto.getUserPassword(),
                        userRequestDto.getUserName()
                )
        );

        return UserResponseDto.builder()
                .id(user.getId())
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .build();
    }

    public UserResponseDto login(UserRequestDto userRequestDto) {
        User user = userRepository.findByUserEmailAndUserPassword(
                userRequestDto.getUserEmail(),
                userRequestDto.getUserPassword()
        ).orElseThrow(LoginException::new);

        return UserResponseDto.builder()
                .id(user.getId())
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .build();
    }

//    public UserResponseDto update(long loginUserId, UserEmail email, UserPassword password, UserName name) {
//        User loginUser = userRepository.findById(loginUserId).
//                orElseThrow();
//        User user = userRepository.findByUserEmail()
//    }
}
