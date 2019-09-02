package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.UserUpdateRequestDto;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.exceptions.DuplicateEmailSignUpException;
import com.woowacourse.edd.exceptions.UnauthorizedAccessException;
import com.woowacourse.edd.exceptions.UserNotFoundException;
import com.woowacourse.edd.repository.UserRepository;
import com.woowacourse.edd.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class UserInternalService {

    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public UserInternalService(UserRepository userRepository, VideoRepository videoRepository) {
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
    }

    public User save(User user) {
        checkEmailDuplication(user);
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailSignUpException();
        }
    }

    private void checkEmailDuplication(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailSignUpException();
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
    }

    public User update(Long id, Long loggedInId, UserUpdateRequestDto userUpdateRequestDto) {
        checkAuthorization(id, loggedInId);
        User user = findById(id);
        user.update(userUpdateRequestDto.getName(), userUpdateRequestDto.getEmail());
        return user;
    }

    public void delete(Long id, Long loggedInId) {
        checkAuthorization(id, loggedInId);
        User user = findById(id);
        user.delete();

        videoRepository.findAllByCreator(user).forEach(video -> video.delete(user.getId()));
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
