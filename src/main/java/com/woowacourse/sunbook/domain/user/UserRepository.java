package com.woowacourse.sunbook.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmailAndUserPassword(UserEmail email, UserPassword password);

    boolean existsByUserEmail(UserEmail userEmail);
}
