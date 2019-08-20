package com.woowacourse.sunbook.seongmo;

import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException(userId));
    }
}
