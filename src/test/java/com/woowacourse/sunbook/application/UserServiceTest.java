package com.woowacourse.sunbook.application;

import com.woowacourse.sunbook.application.dto.UserRequestDto;
import com.woowacourse.sunbook.application.exception.DuplicateEmailException;
import com.woowacourse.sunbook.application.exception.LoginException;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserPassword;
import com.woowacourse.sunbook.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRequestDto userRequestDto;

    @Mock
    private User user;

    @Test
    void 사용자_생성_성공() {
        given(userRepository.save(any(User.class))).willReturn(user);

        userService.save(userRequestDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void 중복_이메일로_인한_사용자_생성_실패() {
        given(userRequestDto.getUserEmail()).willReturn(mock(UserEmail.class));
        given(userRepository.existsByUserEmail(any(UserEmail.class))).willReturn(true);

        assertThrows(DuplicateEmailException.class, () -> {
            userService.save(userRequestDto);
        });
    }

    @Test
    void 로그인_성공() {
        given(userRequestDto.getUserEmail()).willReturn(mock(UserEmail.class));
        given(userRequestDto.getUserPassword()).willReturn(mock(UserPassword.class));
        given(userRepository.findByUserEmailAndUserPassword(any(UserEmail.class), any(UserPassword.class))).willReturn((Optional.of(user)));

        userService.login(userRequestDto);

        verify(userRepository, times(1)).findByUserEmailAndUserPassword(any(UserEmail.class), any(UserPassword.class));
    }

    @Test
    void 로그인_실패() {
        given(userRequestDto.getUserEmail()).willReturn(mock(UserEmail.class));
        given(userRequestDto.getUserPassword()).willReturn(mock(UserPassword.class));
        given(userRepository.findByUserEmailAndUserPassword(any(UserEmail.class), any(UserPassword.class))).willReturn((Optional.empty()));

        assertThrows(LoginException.class, () -> {
            userService.login(userRequestDto);
        });
    }
}