package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.UserRequestDto;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.exceptions.UnauthorizedAccessException;
import com.woowacourse.edd.exceptions.UserNotFoundException;
import com.woowacourse.edd.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserInternalServiceTests {

    private User user;

    @InjectMocks
    UserInternalService userInternalService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = new User("jm", "jm@gmail.com", "p@ssW0rd", false);
    }

    @Test
    void update() {
        UserRequestDto userRequestDto = new UserRequestDto("robby", "kangmin@gmail.com", "p@ssW0rd");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        User updatedUser = userInternalService.update(1L, 1L, userRequestDto);

        assertThat(updatedUser.getEmail()).isEqualTo(userRequestDto.getEmail());
        assertThat(updatedUser.getName()).isEqualTo(userRequestDto.getName());
    }

    @Test
    void update_fail_unauthorized() {
        UserRequestDto userRequestDto = new UserRequestDto("robby", "kangmin@gmail.com", "p@ssW0rd");

        assertThrows(UnauthorizedAccessException.class,
            () -> userInternalService.update(1L, 5L, userRequestDto));
    }

    @Test
    void delete_fail_unauthorized() {
        assertThrows(UnauthorizedAccessException.class,
            () -> userInternalService.delete(1L, 5L));
    }

    @Test
    void delete() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userInternalService.delete(1L, 1L));
    }

    @Test
    void delete_fail() {
        User deletedUser = new User("jm", "jm@gmail.com", "p@ssW0rd", true);
        when(userRepository.findById(any())).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class,
            () -> userInternalService.delete(1L, 1L));
    }
}
