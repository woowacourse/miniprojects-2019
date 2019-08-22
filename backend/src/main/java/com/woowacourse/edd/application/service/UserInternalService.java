package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.UserRequestDto;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.exceptions.UnauthorizedAccessException;
import com.woowacourse.edd.exceptions.UserNotFoundException;
import com.woowacourse.edd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class UserInternalService {

    private final UserRepository userRepository;

    @Autowired
    public UserInternalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
    }

    public User update(Long id, Long loggedInId, UserRequestDto userRequestDto) {
        checkAuthorization(id, loggedInId);
        User user = findById(id);
        user.update(userRequestDto.getName(), userRequestDto.getEmail(), userRequestDto.getPassword());
        return user;
    }

    public void delete(Long id, Long loggedInId) {
        checkAuthorization(id, loggedInId);
        User user = findById(id);
        user.delete();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(UserNotFoundException::new);
    }

    private void checkAuthorization(Long id, Long loggedInId) {
        if (id != loggedInId) {
            throw new UnauthorizedAccessException();
        }
    }
}
