package techcourse.w3.woostagram.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.common.service.StorageService;
import techcourse.w3.woostagram.user.domain.UserRepository;
import techcourse.w3.woostagram.user.dto.UserContentsDto;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.dto.UserUpdateDto;
import techcourse.w3.woostagram.user.exception.LoginException;
import techcourse.w3.woostagram.user.exception.UserCreateException;
import techcourse.w3.woostagram.user.exception.UserUpdateException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    private static final String IMAGE_FILE_URL = "image/file/url";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StorageService storageService;

    private UserDto userDto;
    private UserUpdateDto userUpdateDto;
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .email("a@naver.com")
                .password("Aa1234!!")
                .build();

        multipartFile = new MockMultipartFile(
                "testImage", "testImage.jpg", MediaType.IMAGE_JPEG_VALUE, "<<jpg data>>".getBytes());

        userUpdateDto = UserUpdateDto.builder()
                .userName("woowacrews")
                .contents("woostagram")
                .build();
    }

    @Test
    void create_correct_success() {
        when(userRepository.save(userDto.toEntity())).thenReturn(userDto.toEntity());
        assertThat(userService.save(userDto)).isEqualTo(UserInfoDto.from(userDto.toEntity()));
    }

    @Test
    void create_empty_fail() {
        when(userRepository.save(userDto.toEntity())).thenThrow(RuntimeException.class);
        assertThrows(UserCreateException.class, () -> userService.save(userDto));
    }

    @Test
    void update_correct_success() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userDto.toEntity()));
        when(storageService.saveMultipartFile(multipartFile)).thenReturn(IMAGE_FILE_URL);
        userService.update(userUpdateDto, userDto.getEmail());
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
    }

    @Test
    void update_empty_fail() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserUpdateException.class, () -> userService.update(userUpdateDto, userDto.getEmail()));
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
    }

    @Test
    void delete_correct_success() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userDto.toEntity()));
        userService.deleteByEmail(userDto.getEmail());
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
        verify(userRepository, times(1)).delete(userDto.toEntity());
    }

    @Test
    void delete_empty_fail() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        assertThrows(LoginException.class, () -> userService.deleteByEmail(userDto.getEmail()));
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
        verify(userRepository, times(0)).delete(userDto.toEntity());
    }

    @Test
    void findByEmail_correct_success() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userDto.toEntity()));
        assertThat(userService.findByEmail(userDto.getEmail())).isEqualTo(UserInfoDto.from(userDto.toEntity()));
    }

    @Test
    void findByEmail_empty_fail() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        assertThrows(LoginException.class, () -> userService.findByEmail(userDto.getEmail()));
    }

    @Test
    void findEntityByEmail_correct_success() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userDto.toEntity()));
        assertThat(userService.findUserByEmail(userDto.getEmail())).isEqualTo(userDto.toEntity());
    }

    @Test
    void findEntityByEmail_empty_fail() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        assertThrows(LoginException.class, () -> userService.findByEmail(userDto.getEmail()));
    }

    @Test
    void authUser_correct_success() {
        when(userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword())).thenReturn(Optional.of(userDto.toEntity()));
        assertThat(userService.authUser(userDto)).isEqualTo(userDto.getEmail());
    }

    @Test
    void authUser_empty_fail() {
        when(userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword())).thenReturn(Optional.empty());
        assertThrows(LoginException.class, () -> userService.authUser(userDto));
    }
}