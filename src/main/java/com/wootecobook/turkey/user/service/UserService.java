package com.wootecobook.turkey.user.service;

import com.wootecobook.turkey.user.domain.User;
import com.wootecobook.turkey.user.domain.UserRepository;
import com.wootecobook.turkey.user.service.dto.UserRequest;
import com.wootecobook.turkey.user.service.dto.UserResponse;
import com.wootecobook.turkey.user.service.exception.SignUpException;
import com.wootecobook.turkey.user.service.exception.UserDeleteException;
import com.wootecobook.turkey.user.service.exception.UserMismatchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService {

    public static final String NOT_FOUND_MESSAGE = "유저를 찾을수 없습니다.";

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional(readOnly = true)
    public UserResponse findUserResponseById(Long id) {
        return UserResponse.from(findById(id));
    }

    public UserResponse save(UserRequest userRequest) {
        try {
            return UserResponse.from(userRepository.save(userRequest.toEntity()));
        } catch (Exception e) {
            throw new SignUpException(e.getMessage());
        }
    }

    public void delete(Long userId, Long sessionUserId) {
        matchId(userId, sessionUserId);
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new UserDeleteException();
        }
    }

    private void matchId(Long userId, Long sessionUserId) {
        if (userId == null || !userId.equals(sessionUserId)) {
            throw new UserMismatchException();
        }
    }

    public List<UserResponse> findByName(String name) {
        return userRepository.findTop5ByNameIsContaining(name).stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }
}
