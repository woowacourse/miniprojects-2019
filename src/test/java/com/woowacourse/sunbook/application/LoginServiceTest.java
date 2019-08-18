package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.application.exception.DuplicateEmailException;
import com.woowacourse.sunbook.application.exception.LoginException;
import com.woowacourse.sunbook.application.user.LoginService;
import com.woowacourse.sunbook.application.user.dto.UserRequestDto;
import com.woowacourse.sunbook.application.user.dto.UserResponseDto;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserPassword;
import com.woowacourse.sunbook.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRequestDto userRequestDto;

    @Mock
    private UserResponseDto userResponseDto;

    @Mock
    private User user;

    @Test
    void 사용자_생성_성공() {
        given(modelMapper.map(userRequestDto, User.class)).willReturn(user);
        given(userRepository.save(any(User.class))).willReturn(user);
        given(modelMapper.map(user, UserResponseDto.class)).willReturn(mock(UserResponseDto.class));

        loginService.save(userRequestDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void 중복_이메일로_인한_사용자_생성_실패() {
        given(userRequestDto.getUserEmail()).willReturn(mock(UserEmail.class));
        given(userRepository.existsByUserEmail(any(UserEmail.class))).willReturn(true);

        assertThrows(DuplicateEmailException.class, () -> {
            loginService.save(userRequestDto);
        });
    }

    @Test
    void 로그인_성공() {
        given(userRequestDto.getUserEmail()).willReturn(mock(UserEmail.class));
        given(userRequestDto.getUserPassword()).willReturn(mock(UserPassword.class));
        given(userRepository.findByUserEmailAndUserPassword(any(UserEmail.class), any(UserPassword.class))).willReturn((Optional.of(user)));
        given(modelMapper.map(user, UserResponseDto.class)).willReturn(userResponseDto);

        loginService.login(userRequestDto);

        verify(userRepository, times(1)).findByUserEmailAndUserPassword(any(UserEmail.class), any(UserPassword.class));
    }

    @Test
    void 로그인_실패() {
        given(userRequestDto.getUserEmail()).willReturn(mock(UserEmail.class));
        given(userRequestDto.getUserPassword()).willReturn(mock(UserPassword.class));
        given(userRepository.findByUserEmailAndUserPassword(any(UserEmail.class), any(UserPassword.class))).willReturn((Optional.empty()));

        assertThrows(LoginException.class, () -> {
            loginService.login(userRequestDto);
        });
    }
}