package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.UserRepository;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.AuthUserDto;
import com.woowacourse.dsgram.service.dto.LoginUserDto;
import com.woowacourse.dsgram.service.dto.SignUpUserDto;
import com.woowacourse.dsgram.service.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(SignUpUserDto signUpUserDto) {
        userRepository.save(UserAssembler.toEntity(signUpUserDto));
    }

    public UserDto findUserInfoById(long userId) {
        return UserAssembler.toDto(findById(userId));
    }

    private User findById(long userId) {
        // TODO: 2019-08-14 Exception
        return userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void update(long userId, UserDto userDto) {
        User user = findById(userId);
        user.update(UserAssembler.toEntity(userDto));
    }

    public LoginUserDto login(AuthUserDto authUserDto) {
        User user = userRepository.findByEmail(authUserDto.getEmail())
                .orElseThrow(IllegalArgumentException::new);
        user.checkPassword(authUserDto.getPassword());
        return UserAssembler.toAuthUserDto(user);

    }
}
