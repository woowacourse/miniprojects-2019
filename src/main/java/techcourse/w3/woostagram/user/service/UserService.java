package techcourse.w3.woostagram.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import techcourse.w3.woostagram.common.service.StorageService;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserRepository;
import techcourse.w3.woostagram.user.dto.UserCreateDto;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.dto.UserUpdateDto;
import techcourse.w3.woostagram.user.exception.LoginException;
import techcourse.w3.woostagram.user.exception.UserCreateException;
import techcourse.w3.woostagram.user.exception.UserNotFoundException;
import techcourse.w3.woostagram.user.exception.UserProfileException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final StorageService storageService;

    public UserService(final UserRepository userRepository, final StorageService storageService) {
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    public UserInfoDto save(UserCreateDto userDto) {
        checkDuplicatedEmail(userDto.getEmail());
        checkDuplicatedUserName(userDto.getUserName());

        try {
            return UserInfoDto.from(userRepository.save(userDto.toEntity()));
        } catch (Exception error) {
            throw new UserCreateException(error.getMessage());
        }
    }

    private void checkDuplicatedEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserCreateException("이미 존재하는 아이디입니다.");
        }
    }

    private void checkDuplicatedUserName(String userName) {
        if (userRepository.findByUserContents_UserName(userName).isPresent()) {
            throw new UserCreateException("이미 존재하는 사용자 이름입니다.");
        }
    }

    public UserInfoDto authUser(UserDto userDto) {
        return userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword())
                .map(UserInfoDto::from)
                .orElseThrow(LoginException::new);
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

    public UserInfoDto findByUserName(String userName) {
        return UserInfoDto.from(findUserByUserName(userName));
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserContents_UserName(userName).orElseThrow(UserNotFoundException::new);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User findById(long targetId) {
        return userRepository.findById(targetId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public String uploadProfileImage(MultipartFile profileImage, String email) {
        if (profileImage.isEmpty()) {
            throw new UserProfileException();
        }
        User user = findUserByEmail(email);
        deleteUserProfileImage(user);
        return uploadFile(profileImage, user);
    }

    private String uploadFile(MultipartFile profileImage, User user) {
        String fileUrl = storageService.saveMultipartFile(profileImage);
        user.updateProfile(fileUrl);

        return fileUrl;
    }

    @Transactional
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

    public List<UserInfoDto> findByUsernameContaining(String query) {
        return userRepository.findTop10ByUserContents_UserNameContainingIgnoreCaseOrderByUserContents_UserName(query).stream()
                .map(UserInfoDto::from)
                .collect(Collectors.toList());
    }
}
