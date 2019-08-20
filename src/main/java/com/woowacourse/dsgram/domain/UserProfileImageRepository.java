package com.woowacourse.dsgram.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, Long> {
    boolean existsById(Long id);
}
