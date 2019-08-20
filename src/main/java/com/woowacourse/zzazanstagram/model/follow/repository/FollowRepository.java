package com.woowacourse.zzazanstagram.model.follow.repository;

import com.woowacourse.zzazanstagram.model.follow.domain.Follow;
import com.woowacourse.zzazanstagram.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowee(Member followee);

    List<Follow> findByFollower(Member follower);
}
