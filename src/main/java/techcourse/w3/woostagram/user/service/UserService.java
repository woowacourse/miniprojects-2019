package techcourse.w3.woostagram.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import techcourse.w3.woostagram.common.service.StorageService;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserRepository;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.dto.UserProfileImageDto;
import techcourse.w3.woostagram.user.dto.UserUpdateDto;
import techcourse.w3.woostagram.user.exception.LoginException;
import techcourse.w3.woostagram.user.exception.UserCreateException;
import techcourse.w3.woostagram.user.exception.UserUpdateException;

@Service
public class UserService {
    private static final String DEFAULT_PROFILE_IMAGE =
            "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/default_profile_image.jpg";

    private final UserRepository userRepository;
    private final StorageService storageService;

    public UserService(final UserRepository userRepository, final StorageService storageService) {
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    public UserInfoDto save(UserDto userDto) {
        try {
            return UserInfoDto.from(userRepository.save(userDto.toEntity()));
        } catch (Exception error) {
            throw new UserCreateException();
        }
    }

    public String authUser(UserDto userDto) {
        return userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword())
                .orElseThrow(LoginException::new).getEmail();
    }

    @Transactional
    public void update(UserUpdateDto userUpdateDto, String email) {
        User user = findUserByEmail(email);
        user.updateContents(userUpdateDto.toEntity());
    }

    private void deleteFile(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl) && !fileUrl.equals(DEFAULT_PROFILE_IMAGE)) {
            storageService.deleteFile(fileUrl);
        }
    }

    public void deleteByEmail(String email) {
        userRepository.delete(findUserByEmail(email));
    }

    public UserInfoDto findByEmail(String email) {
        return UserInfoDto.from(findUserByEmail(email));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(LoginException::new);
    }

    public User findById(long targetId) {
        return userRepository.findById(targetId).orElseThrow(LoginException::new);
    }

    @Transactional
    public String uploadProfileImage(UserProfileImageDto userProfileImageDto, String email) {
        User user = findUserByEmail(email);
        String fileUrl = userProfileImageDto.getOriginalImageFile();
        deleteFile(fileUrl);
        if (!userProfileImageDto.getImageFile().isEmpty()) {
            fileUrl = storageService.saveMultipartFile(userProfileImageDto.getImageFile());
        }
        user.updateProfile(fileUrl);
        return fileUrl;
    }
}
