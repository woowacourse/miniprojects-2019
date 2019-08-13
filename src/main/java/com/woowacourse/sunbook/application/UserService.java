package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.application.dto.UserResponseDto;
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

    public UserResponseDto save(UserEmail email, UserPassword password, UserName name) {
        User user = userRepository.save(new User(email, password, name));

        return UserResponseDto.builder()
                .id(user.getId())
                .userEmail(user.getUserEmail())
                .userName(user.getName())
                .build();
    }
}
