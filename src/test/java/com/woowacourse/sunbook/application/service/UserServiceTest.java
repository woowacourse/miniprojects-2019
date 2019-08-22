package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.dto.user.UserUpdateRequestDto;
import com.woowacourse.sunbook.application.exception.LoginException;
import com.woowacourse.sunbook.domain.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserUpdateRequestDto userUpdateRequestDto;

    @Mock
    private UserChangePassword userChangePassword;

    @Mock
    private User user;

    @Mock
    private UserResponseDto userResponseDto;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserEmail userEmail;

    @Mock
    private UserName userName;

    @Mock
    private UserPassword userPassword;

    @Test
    void 사용자_수정() {
        given(userResponseDto.getUserEmail()).willReturn(mock(UserEmail.class));
        given(userUpdateRequestDto.getUserEmail()).willReturn(userEmail);
        given(userUpdateRequestDto.getUserName()).willReturn(userName);
        given(userUpdateRequestDto.getUserPassword()).willReturn(userPassword);
        given(userRepository.findByUserEmailAndUserPassword(any(UserEmail.class), any(UserPassword.class)))
                .willReturn((Optional.of(user)));
        given(modelMapper.map(user, UserResponseDto.class)).willReturn(userResponseDto);
        given(userUpdateRequestDto.getChangePassword()).willReturn(userChangePassword);
        given(userChangePassword.updatedPassword(userPassword)).willReturn(userPassword);

        userService.update(userResponseDto, userUpdateRequestDto);

        verify(user, times(1)).updateEmail(user, userEmail);
        verify(user, times(1)).updateName(user, userName);
        verify(user, times(1)).updatePassword(user, userPassword);
    }

    @Test
    void 사용자_수정_권한_없음() {
        given(userRepository.findByUserEmailAndUserPassword(any(UserEmail.class), any(UserPassword.class)))
                .willReturn((Optional.empty()));

        assertThrows(LoginException.class, () -> {
            userService.update(userResponseDto, userUpdateRequestDto);
        });
    }
}