package com.woowacourse.edd.repository;

import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findAllByCreator_Id(Long creatorId);

    List<Video> findAllByCreator(User creator);
}
