package techcourse.w3.woostagram.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.w3.woostagram.common.service.StorageService;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserRepository;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.dto.UserProfileImageDto;
import techcourse.w3.woostagram.user.dto.UserUpdateDto;
import techcourse.w3.woostagram.user.exception.LoginException;
import techcourse.w3.woostagram.user.exception.UserCreateException;
import techcourse.w3.woostagram.user.exception.UserNotFoundException;
import techcourse.w3.woostagram.user.exception.UserProfileException;

@Service
public class UserService {
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

    public void deleteByEmail(String email) {
        userRepository.delete(findUserByEmail(email));
    }

    public UserInfoDto findByEmail(String email) {
        return UserInfoDto.from(findUserByEmail(email));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User findById(long targetId) {
        return userRepository.findById(targetId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public String uploadProfileImage(UserProfileImageDto userProfileImageDto, String email) {
        if (userProfileImageDto.getImageFile().isEmpty()) {
            throw new UserProfileException();
        }
        User user = findUserByEmail(email);
        deleteUserProfileImage(user);
        return uploadFile(userProfileImageDto, user);
    }

    private String uploadFile(UserProfileImageDto userProfileImageDto, User user) {
        String fileUrl = storageService.saveMultipartFile(userProfileImageDto.getImageFile());
        user.updateProfile(fileUrl);

        return fileUrl;
    }

    public String deleteProfileImage(String email) {
        User user = findUserByEmail(email);
        deleteUserProfileImage(user);
        return user.makeProfileDefault();
    }

    private void deleteUserProfileImage(User user) {
        if (!user.hasDefaultImage()) {
            storageService.deleteFile(user.getProfile());
        }
    }
}
