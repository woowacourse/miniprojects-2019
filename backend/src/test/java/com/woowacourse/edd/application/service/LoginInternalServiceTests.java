package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.exceptions.PasswordNotMatchException;
import com.woowacourse.edd.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LoginInternalServiceTests {

    @Mock
    private UserInternalService userInternalService;

    @InjectMocks
    private LoginInternalService loginInternalService;

    @Test
    void authenticate_succeed() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("aaa@bbb.com", "p@ssW0rd");
        User user = new User("robby", "aaa@bbb.com", "p@ssW0rd", false);

        when(userInternalService.findByEmail(loginRequestDto.getEmail())).thenReturn(user);

        User succeedUser = loginInternalService.authenticate(loginRequestDto);
        assertThat(succeedUser.getName()).isEqualTo(user.getName());
        assertThat(succeedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void authenticate_invalid_email() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("aaa@bbb.com", "p@ssW0rd");

        when(userInternalService.findByEmail(anyString())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> {
            loginInternalService.authenticate(loginRequestDto);
        });
    }

    @Test
    void authenticate_invalid_password() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("aaa@bbb.com", "p@ssWord2");
        User user = new User("robby", "aaa@bbb.com", "p@ssW0rd", false);

        when(userInternalService.findByEmail(loginRequestDto.getEmail())).thenReturn(user);

        assertThrows(PasswordNotMatchException.class, () -> {
            loginInternalService.authenticate(loginRequestDto);
        });

    }

}
