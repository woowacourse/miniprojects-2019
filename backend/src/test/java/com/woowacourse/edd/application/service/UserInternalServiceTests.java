package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.UserUpdateRequestDto;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.exceptions.UnauthorizedAccessException;
import com.woowacourse.edd.exceptions.UserNotFoundException;
import com.woowacourse.edd.repository.UserRepository;
import com.woowacourse.edd.repository.VideoRepository;
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

    @Mock
    private VideoRepository videoRepository;

    @BeforeEach
    void setUp() {
        user = new User("jm", "jm@gmail.com", "p@ssW0rd");
    }

    @Test
    void update() {
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("robby", "kangmin@gmail.com");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        User updatedUser = userInternalService.update(1L, 1L, userUpdateRequestDto);

        assertThat(updatedUser.getEmail()).isEqualTo(userUpdateRequestDto.getEmail());
        assertThat(updatedUser.getName()).isEqualTo(userUpdateRequestDto.getName());
    }

    @Test
    void update_fail_unauthorized() {
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("robby", "kangmin@gmail.com");

        assertThrows(UnauthorizedAccessException.class,
            () -> userInternalService.update(1L, 5L, userUpdateRequestDto));
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
        User deletedUser = new User("jm", "jm@gmail.com", "p@ssW0rd");
        deletedUser.delete();
        when(userRepository.findById(any())).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class,
            () -> userInternalService.delete(1L, 1L));
    }
}
