package com.wootecobook.turkey.user.domain;

import com.wootecobook.turkey.user.service.dto.IntroductionResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntroductionRepository extends JpaRepository<Introduction, Long> {

    Optional<Introduction> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
