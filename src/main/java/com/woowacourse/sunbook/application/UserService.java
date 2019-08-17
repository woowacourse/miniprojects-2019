package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.exception.DuplicateEmailException;
import com.woowacourse.sunbook.application.exception.LoginException;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto) {
        checkDuplicateEmail(userRequestDto);
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
