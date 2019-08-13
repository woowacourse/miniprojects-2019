package techcourse.w3.woostagram.user.service;

import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.user.domain.UserRepository;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfoDto create(UserDto userDto) {
        return UserInfoDto.from(userRepository.save(userDto.toEntity()));
    }

}
