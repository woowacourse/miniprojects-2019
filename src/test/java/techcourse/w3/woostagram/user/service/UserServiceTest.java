package techcourse.w3.woostagram.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.common.service.StorageService;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserRepository;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.dto.UserUpdateDto;
import techcourse.w3.woostagram.user.exception.LoginException;
import techcourse.w3.woostagram.user.exception.UserCreateException;
import techcourse.w3.woostagram.user.exception.UserNotFoundException;
import techcourse.w3.woostagram.user.exception.UserProfileException;

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
    private User user;
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

        user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .profile("profileUrl")
                .build();
    }

    @Test
    void create_correct_isOk() {
        when(userRepository.save(userDto.toEntity())).thenReturn(userDto.toEntity());
        assertThat(userService.save(userDto)).isEqualTo(UserInfoDto.from(userDto.toEntity()));
    }

    @Test
    void create_empty_isFail() {
        when(userRepository.save(userDto.toEntity())).thenThrow(RuntimeException.class);
        assertThrows(UserCreateException.class, () -> userService.save(userDto));
    }

    @Test
    void update_correct_isOk() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userDto.toEntity()));
        when(storageService.saveMultipartFile(multipartFile)).thenReturn(IMAGE_FILE_URL);
        userService.update(userUpdateDto, userDto.getEmail());
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
    }

    @Test
    void update_empty_isFail() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.update(userUpdateDto, userDto.getEmail()));
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
    }

    @Test
    void delete_correct_isOk() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userDto.toEntity()));
        userService.deleteByEmail(userDto.getEmail());
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
        verify(userRepository, times(1)).delete(userDto.toEntity());
    }

    @Test
    void delete_empty_isFail() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.deleteByEmail(userDto.getEmail()));
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
        verify(userRepository, times(0)).delete(userDto.toEntity());
    }

    @Test
    void findByEmail_correct_isOk() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userDto.toEntity()));
        assertThat(userService.findByEmail(userDto.getEmail())).isEqualTo(UserInfoDto.from(userDto.toEntity()));
    }

    @Test
    void findByEmail_empty_isFail() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findByEmail(userDto.getEmail()));
    }

    @Test
    void findEntityByEmail_correct_isOk() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userDto.toEntity()));
        assertThat(userService.findUserByEmail(userDto.getEmail())).isEqualTo(userDto.toEntity());
    }

    @Test
    void findEntityByEmail_empty_isFail() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findByEmail(userDto.getEmail()));
    }

    @Test
    void findByUserName_correctName_isOk() {
        when(userRepository.findByUserContents_UserName(anyString())).thenReturn(Optional.of(userDto.toEntity()));
        assertThat(userService.findByUserName(anyString()).getEmail()).isEqualTo(userDto.getEmail());

    }

    @Test
    void findByUserName_wrongName_isFail() {
        when(userRepository.findByUserContents_UserName(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findByUserName(anyString()));
    }

    @Test
    void findById_correctId_isOk() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userDto.toEntity()));
        assertThat(userService.findById(anyLong()).getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void findById_wrongId_isFail() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findById(anyLong()));
    }

    @Test
    void authUser_correct_isOk() {
        when(userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword())).thenReturn(Optional.of(userDto.toEntity()));
        assertThat(userService.authUser(userDto).getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void authUser_empty_isFail() {
        when(userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword())).thenReturn(Optional.empty());
        assertThrows(LoginException.class, () -> userService.authUser(userDto));
    }

    @Test
    void uploadProfileImage_correctFileImageAndEmail_isOk() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(storageService.saveMultipartFile(multipartFile)).thenReturn(IMAGE_FILE_URL);
        doNothing().when(storageService).deleteFile(anyString());
        assertThat(userService.uploadProfileImage(multipartFile, anyString())).isEqualTo(IMAGE_FILE_URL);
    }

    @Test
    void uploadProfileImage_wrongFileImage_isFail() {
        MultipartFile wrongMultipartFile = new MockMultipartFile(
                "testImage", "", MediaType.IMAGE_JPEG_VALUE, new byte[0]);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        assertThrows(UserProfileException.class, () -> userService.uploadProfileImage(wrongMultipartFile, userDto.getEmail()));
    }

    @Test
    void deleteProfileImage_correct_isOk() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
        doNothing().when(storageService).deleteFile(anyString());

        userService.deleteProfileImage(userDto.getEmail());
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());
    }
}