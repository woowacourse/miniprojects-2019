package com.woowacourse.edd.repository;

import com.woowacourse.edd.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
