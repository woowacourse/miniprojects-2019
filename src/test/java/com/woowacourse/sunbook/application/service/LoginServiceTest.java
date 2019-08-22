package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.exception.LoginException;
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