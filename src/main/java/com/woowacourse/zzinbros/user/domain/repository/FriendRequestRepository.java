package com.woowacourse.zzinbros.user.domain.repository;

import com.woowacourse.zzinbros.user.domain.FriendRequest;
import com.woowacourse.zzinbros.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Set<FriendRequest> findAllBySender(User sender);

    Set<FriendRequest> findAllByReceiver(User receiver);

    void deleteBySenderAndReceiver(User sender, User receiver);

    boolean existsBySenderAndReceiver(User sender, User receiver);
}
