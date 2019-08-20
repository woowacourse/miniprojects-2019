package com.woowacourse.edd.repository;

import com.woowacourse.edd.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
