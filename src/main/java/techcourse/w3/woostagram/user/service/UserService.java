package techcourse.w3.woostagram.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserRepository;
import techcourse.w3.woostagram.user.dto.UserContentsDto;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.exception.LoginException;

@Service
public class UserService {
    private static final String ERROR_USER_NOT_FOUND = "유저를 찾을 수 없습니다.";

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfoDto create(UserDto userDto) {
        return UserInfoDto.from(userRepository.save(userDto.toEntity()));
    }

    public String authUser(UserDto userDto) {
        return userRepository.findUserByEmailAndPassword(userDto.getEmail(), userDto.getPassword())
                .orElseThrow(() -> new LoginException(ERROR_USER_NOT_FOUND)).getEmail();
    }

    @Transactional
    public void update(UserContentsDto userContentsDto, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new LoginException(ERROR_USER_NOT_FOUND));
        user.contentsUpdated(userContentsDto.toEntity());
    }

    public void delete(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new LoginException(ERROR_USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
