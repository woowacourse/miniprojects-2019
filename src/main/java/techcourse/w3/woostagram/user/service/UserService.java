package techcourse.w3.woostagram.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.domain.UserRepository;
import techcourse.w3.woostagram.user.dto.UserContentsDto;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;
import techcourse.w3.woostagram.user.exception.LoginException;
import techcourse.w3.woostagram.user.exception.UserCreateException;
import techcourse.w3.woostagram.user.exception.UserUpdateException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public void update(UserContentsDto userContentsDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserUpdateException::new);
        user.updateContents(userContentsDto.toEntity());
    }

    public void deleteByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(LoginException::new);
        userRepository.delete(user);
    }

    public UserInfoDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(LoginException::new);
        return UserInfoDto.from(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(LoginException::new);
    }
}
