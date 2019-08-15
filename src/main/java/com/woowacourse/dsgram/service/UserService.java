package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.UserRepository;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.user.AuthUserDto;
import com.woowacourse.dsgram.service.dto.user.LoginUserDto;
import com.woowacourse.dsgram.service.dto.user.SignUpUserDto;
import com.woowacourse.dsgram.service.dto.user.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(SignUpUserDto signUpUserDto) {
        checkDuplicatedAttributes(signUpUserDto);
        userRepository.save(UserAssembler.toEntity(signUpUserDto));
    }

    private void checkDuplicatedAttributes(SignUpUserDto signUpUserDto) {
        if (userRepository.countByNickName(signUpUserDto.getNickName()) > 0) {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        }
        if (userRepository.findByEmail(signUpUserDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }
    }

    public UserDto findUserInfoById(long userId, LoginUserDto loginUserDto) {
        User user = findById(userId);
        user.checkEmail(loginUserDto.getEmail());
        return UserAssembler.toDto(findById(userId));
    }

    private User findById(long userId) {
        // TODO: 2019-08-14 Exception
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void update(long userId, UserDto userDto, LoginUserDto loginUserDto) {
        User user = findById(userId);

        // TODO: 2019-08-15 사장님한테 검사 맞기 (domain 안에 넣어야 하나?)
        if (!user.equalsNickName(userDto.getNickName()) &&
                userRepository.countByNickName(userDto.getNickName()) > 0) {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        }

        user.update(UserAssembler.toEntity(userDto), loginUserDto.getEmail());
    }

    public LoginUserDto login(AuthUserDto authUserDto) {
        User user = userRepository.findByEmail(authUserDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Plz check your account."));
        user.checkPassword(authUserDto.getPassword());
        return UserAssembler.toAuthUserDto(user);
    }
}
