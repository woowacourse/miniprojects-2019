package com.woowacourse.dsgram.service;

import com.google.gson.JsonElement;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.UserRepository;
import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.user.AuthUserDto;
import com.woowacourse.dsgram.service.dto.user.LoginUserDto;
import com.woowacourse.dsgram.service.dto.user.SignUpUserDto;
import com.woowacourse.dsgram.service.dto.user.UserDto;
import com.woowacourse.dsgram.service.exception.DuplicatedAttributeException;
import com.woowacourse.dsgram.service.exception.NotFoundUserException;
import com.woowacourse.dsgram.service.oauth.GithubClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final GithubClient githubClient;

    public UserService(UserRepository userRepository, GithubClient githubClient) {
        this.userRepository = userRepository;
        this.githubClient = githubClient;
    }

    public void save(SignUpUserDto signUpUserDto) {
        checkDuplicatedAttributes(signUpUserDto.getNickName(), signUpUserDto.getEmail());
        userRepository.save(UserAssembler.toEntity(signUpUserDto));
    }

    private void checkDuplicatedAttributes(String nickName, String email) {
        if (userRepository.countByNickName(nickName) > 0) {
            throw new DuplicatedAttributeException("이미 사용중인 닉네임입니다.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicatedAttributeException("이미 사용중인 이메일입니다.");
        }
    }

    public UserDto findUserInfoById(long userId, LoginUserDto loginUserDto) {
        User user = findById(userId);
        user.checkEmail(loginUserDto.getEmail());
        return UserAssembler.toDto(findById(userId));
    }

    private User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void update(long userId, UserDto userDto, LoginUserDto loginUserDto) {
        User user = findById(userId);
        checkDuplicatedNickName(userDto, user);
        user.update(UserAssembler.toEntity(userDto), loginUserDto.getEmail());
    }

    private void checkDuplicatedNickName(UserDto userDto, User user) {
        if (!user.equalsNickName(userDto.getNickName()) &&
                userRepository.countByNickName(userDto.getNickName()) > 0) {
            throw new DuplicatedAttributeException("이미 사용중인 닉네임입니다.");
        }
    }

    public LoginUserDto login(AuthUserDto authUserDto) {
        User user = findByEmail(authUserDto.getEmail())
                .orElseThrow(() -> new InvalidUserException("회원정보가 일치하지 않습니다."));
        user.checkPassword(authUserDto.getPassword());
        return UserAssembler.toAuthUserDto(user);
    }

    private Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public LoginUserDto oauth(String code) {
        String accessToken = githubClient.getToken(code);

        String email = githubClient.getUserEmail(accessToken);

        Optional<User> user = findByEmail(email);
        if (user.isPresent()) {
            return UserAssembler.toAuthUserDto(user.get());
        }
        return UserAssembler.toAuthUserDto(saveOauthUser(accessToken, email));
    }

    private User saveOauthUser(String accessToken, String email) {
        // TODO: 2019-08-16 Nick name을 확인해야 하나...? DB를 따로 관리해야하나...?
        JsonElement userInfo = githubClient.getUserInformation(accessToken);
        return userRepository.save(UserAssembler.toEntity(email, userInfo.getAsJsonObject()));
    }
}
