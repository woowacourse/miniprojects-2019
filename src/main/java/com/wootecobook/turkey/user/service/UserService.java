package com.wootecobook.turkey.user.service;

import com.wootecobook.turkey.user.domain.User;
import com.wootecobook.turkey.user.domain.UserRepository;
import com.wootecobook.turkey.user.service.dto.UserRequest;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import com.wootecobook.turkey.user.service.exception.NotFoundUserException;
import com.wootecobook.turkey.user.service.exception.SignUpException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundUserException::new);
    }

    public UserResponse findUserResponseById(Long id) {
        return UserResponse.from(findById(id));
    }

    public User save(UserRequest userRequest) {
        try {
            return userRepository.save(userRequest.toEntity());
        } catch (Exception e) {
            throw new SignUpException(e.getMessage());
        }
    }

}
