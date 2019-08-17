package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.UserRepository;
import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import com.woowacourse.dsgram.service.dto.user.AuthUserDto;
import com.woowacourse.dsgram.service.dto.user.LoginUserDto;
import com.woowacourse.dsgram.service.dto.user.SignUpUserDto;
import com.woowacourse.dsgram.service.dto.user.UserDto;
import com.woowacourse.dsgram.service.exception.NotFoundUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    private final SignUpUserDto signUpUserDto = SignUpUserDto.builder()
            .email("buddy@buddy.com")
            .userName("김버디")
            .nickName("buddy_")
            .password("Aa12345!")
            .build();

    private final User user = User.builder()
            .email("buddy@buddy.com")
            .userName("김버디")
            .nickName("buddy_")
            .password("Aa12345!")
            .build();
    
    private final AuthUserDto authUserDto = new AuthUserDto("buddy@buddy.com","Aa12345!");

    private final UserDto userDto = new UserDto(1L, "김버디", "buddy_2", "Aa12345!", "www.website.com", "intro");
    private final LoginUserDto loginUserDto = new LoginUserDto("buddy@buddy.com","buddy_","김버디");


    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void 유저_저장_성공() {
        given(userRepository.save(user)).willReturn(user);

        userService.save(signUpUserDto);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void 유저_저장_실패_닉네임_중복() {
        given(userRepository.countByNickName(any())).willReturn(1L);

        assertThrows(RuntimeException.class, () -> {userService.save(signUpUserDto);});
    }

    @Test
    void 유저_저장_실패_이메일_중복() {
        given(userRepository.findByEmail(any())).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.save(signUpUserDto));
    }

    @Test
    void 로그인_성공() {
        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));

        userService.login(authUserDto);

        verify(userRepository,times(1)).findByEmail(any());
    }

    @Test
    void 로그인_실패_이메일_존재안함() {
        given(userRepository.findByEmail(any())).willReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.login(authUserDto));
    }

    @Test
    void 로그인_실패_패스워드_불일치() {
        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));

        assertThrows(InvalidUserException.class, () -> userService.login(new AuthUserDto("buddy@buddy.com","exception")));
    }

    @Test
    void 유저_정보_수정_성공() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        UserDto userDto = new UserDto(1L, "김버디", "buddy_2", "Aa12345!", "www.website.com", "intro");
        LoginUserDto loginUserDto = new LoginUserDto("buddy@buddy.com","buddy_","김버디");
        assertDoesNotThrow(() -> userService.update(1L, userDto, loginUserDto));
    }

    @Test
    void 유저_정보_실패_없는_유저() {
        given(userRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(NotFoundUserException.class, () -> userService.update(any(), userDto, loginUserDto));
    }

    @Test
    void 남의_정보를_수정() {
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(userRepository.countByNickName(any())).willReturn(0L);
        assertThrows(InvalidUserException.class, () -> userService.update(any(), userDto, loginUserDto));
    }

    @Test
    void 유저_수정_실패_닉네임_중복() {
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(userRepository.countByNickName(any())).willReturn(1L);
        assertThrows(InvalidUserException.class, () -> userService.update(any(), userDto, loginUserDto));
    }

}
