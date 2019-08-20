package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.UserConverter;
import com.woowacourse.edd.application.dto.UserRequestDto;
import com.woowacourse.edd.application.response.UserResponse;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.exceptions.UserNotFoundException;
import com.woowacourse.edd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter = new UserConverter();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long save(UserRequestDto userSaveRequestDto) {
        User user = userConverter.toSaveEntity(userSaveRequestDto);
        user = userRepository.save(user);
        return user.getId();
    }

    public UserResponse update(UserRequestDto userRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.update(userRequestDto);
        return userConverter.toResponse(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (user.isDeleted()) {
            throw new UserNotFoundException();
        }
        user.delete();
    }

    @Transactional(readOnly = true)
    public UserResponse findbyId(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userConverter.toResponse(user);
    }
}
